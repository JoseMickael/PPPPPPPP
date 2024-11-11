package agendaDeContatos.swing;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Agenda extends JFrame {
	

	    private JTextField txtNome, txtEmail, txtTelefone, txtEndereco;
	    private JComboBox<String> cbSexo, cbEstadoCivil;
	    private JButton btnAdicionar, btnListar, btnDeletar, btnAtualizar;
	    private JTable table;
	    private DefaultTableModel model;

	    private Connection con;

	    public Agenda() {
	        // Configurações da janela
	        setTitle("Agenda de Contatos");
	        setSize(600, 400);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLayout(new BorderLayout());

	        // Painel para os campos de entrada
	        JPanel panelInput = new JPanel();
	        panelInput.setLayout(new GridLayout(6, 2));

	        // Campos de entrada
	        panelInput.add(new JLabel("Nome:"));
	        txtNome = new JTextField();
	        panelInput.add(txtNome);

	        panelInput.add(new JLabel("Email:"));
	        txtEmail = new JTextField();
	        panelInput.add(txtEmail);

	        panelInput.add(new JLabel("Telefone:"));
	        txtTelefone = new JTextField();
	        panelInput.add(txtTelefone);

	        panelInput.add(new JLabel("Endereço:"));
	        txtEndereco = new JTextField();
	        panelInput.add(txtEndereco);

	        panelInput.add(new JLabel("Sexo:"));
	        cbSexo = new JComboBox<>(new String[]{"Masculino", "Feminino"});
	        panelInput.add(cbSexo);

	        panelInput.add(new JLabel("Estado Civil:"));
	        cbEstadoCivil = new JComboBox<>(new String[]{"Solteiro", "Casado"});
	        panelInput.add(cbEstadoCivil);

	        // Botões
	        btnAdicionar = new JButton("Adicionar");
	        btnListar = new JButton("Listar");
	        btnDeletar = new JButton("Deletar");
	        btnAtualizar = new JButton("Atualizar");

	        // Adicionando os botões ao painel
	        JPanel panelButtons = new JPanel();
	        panelButtons.add(btnAdicionar);
	        panelButtons.add(btnListar);
	        panelButtons.add(btnDeletar);
	        panelButtons.add(btnAtualizar);

	        // Tabela para exibir contatos
	        model = new DefaultTableModel(new String[]{"ID", "Nome", "Email", "Telefone", "Endereço", "Sexo", "Estado Civil"}, 0);
	        table = new JTable(model);

	        // Adicionando componentes à janela
	        add(panelInput, BorderLayout.NORTH);
	        add(new JScrollPane(table), BorderLayout.CENTER);
	        add(panelButtons, BorderLayout.SOUTH);

	        // Ação dos botões
	        btnAdicionar.addActionListener(e -> adicionarContato());
	        btnListar.addActionListener(e -> listarContatos());
	        btnDeletar.addActionListener(e -> deletarContato());
	        btnAtualizar.addActionListener(e -> atualizarContato());

	        // Conexão com o banco de dados
	        con = ConexaoDb.conectar();
	    }

	    private void adicionarContato() {
	        try {
	            String sql = "INSERT INTO contato (nome, email, telefone, endereco, sexo, estado_civil) VALUES (?, ?, ?, ?, ?, ?)";
	            PreparedStatement stmt = con.prepareStatement(sql);
	            stmt.setString(1, txtNome.getText());
	            stmt.setString(2, txtEmail.getText());
	            stmt.setString(3, txtTelefone.getText());
	            stmt.setString(4, txtEndereco.getText());
	            stmt.setString(5, cbSexo.getSelectedItem().toString());
	            stmt.setString(6, cbEstadoCivil.getSelectedItem().toString());
	            stmt.executeUpdate();
	            JOptionPane.showMessageDialog(this, "Contato adicionado com sucesso!");
	            txtNome.setText("");
	            txtEmail.setText("");
	            txtTelefone.setText("");
	            txtEndereco.setText("");
	            cbSexo.setSelectedIndex(0);
	            cbEstadoCivil.setSelectedIndex(0);
	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(this, "Erro ao adicionar contato: " + e.getMessage());
	        }
	    }

	    private void listarContatos() {
	        try {
	            String sql = "SELECT * FROM contato";
	            PreparedStatement stmt = con.prepareStatement(sql);
	            ResultSet rs = stmt.executeQuery();
	            model.setRowCount(0); // Limpa a tabela

	            while (rs.next()) {
	                model.addRow(new Object[]{
	                    rs.getInt("id"),
	                    rs.getString("nome"),
	                    rs.getString("email"),
	                    rs.getString("telefone"),
	                    rs.getString("endereco"),
	                    rs.getString("sexo"),
	                    rs.getString("estado_civil")
	                });
	            }
	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(this, "Erro ao listar contatos: " + e.getMessage());
	        }
	    }

	    private void deletarContato() {
	        int selectedRow = table.getSelectedRow();
	        if (selectedRow != -1) {
	            int id = (int) model.getValueAt(selectedRow, 0);
	            try {
	                String sql = "DELETE FROM contato WHERE id = ?";
	                PreparedStatement stmt = con.prepareStatement(sql);
	                stmt.setInt(1, id);
	                stmt.executeUpdate();
	                model.removeRow(selectedRow);
	                JOptionPane.showMessageDialog(this, "Contato deletado com sucesso!");
	            } catch (SQLException e) {
	                JOptionPane.showMessageDialog(this, "Erro ao deletar contato: " + e.getMessage());
	            }
	        } else {
	            JOptionPane.showMessageDialog(this, "Selecione um contato para deletar.");
	        }
	    }

	    private void atualizarContato() {
	        int selectedRow = table.getSelectedRow();
	        if (selectedRow != -1) {
	            int id = (int) model.getValueAt(selectedRow, 0);
	            try {
	                String sql = "UPDATE contato SET nome = ?, email = ?, telefone = ?, endereco = ?, sexo = ?, estado_civil = ? WHERE id = ?";
	                PreparedStatement stmt = con.prepareStatement(sql);
	                stmt.setString(1, txtNome.getText());
	                stmt.setString(2, txtEmail.getText());
	                stmt.setString(3, txtTelefone.getText());
	                stmt.setString(4, txtEndereco.getText());
	                stmt.setString(5, cbSexo.getSelectedItem().toString());
	                stmt.setString(6, cbEstadoCivil.getSelectedItem().toString());
	                stmt.setInt(7, id);
	                stmt.executeUpdate();
	                JOptionPane.showMessageDialog(this, "Contato atualizado com sucesso!");
	            } catch (SQLException e) {
	                JOptionPane.showMessageDialog(this, "Erro ao atualizar contato: " + e.getMessage());
	            }
	        } else {
	            JOptionPane.showMessageDialog(this, "Selecione um contato para atualizar.");
	        }
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            Agenda a = new Agenda();
	            a.setVisible(true);
	        });
	    }
	}


