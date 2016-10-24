import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;


public class Janela extends JFrame implements ActionListener{
	
	private Grammar gramatica;
	private JPanel PanelTable,PanelTexts,PanelEntrada;
	private JTextField txtTestado,txtNTestado;
	private JTextField txtrProducao,txtiEstado;
	private JTextField textInput;
	private JButton newValue,verificar,editar; 
	
	public Janela(){
		super("Gramática Formal");

		PanelTexts = new JPanel(new MigLayout());
		PanelTexts.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		PanelTexts.setBounds(0, 0, 530, 350);
		
		PanelTable = new JPanel(new MigLayout());
		PanelTable.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		PanelTable.setBounds(0, 220, 805, 450);
	
		PanelEntrada = new JPanel(new MigLayout());
		PanelEntrada.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		PanelEntrada.setBounds(0, 500, 500, 350);
		
		txtTestado = new JTextField(10);
		txtNTestado =  new JTextField(10);
		txtrProducao = new JTextField(10);
		txtiEstado =  new JTextField(10);
		textInput = new JTextField(10);
		
		PanelTexts.add(new JLabel("Estados terminais - Ex: a,b,c "));
		PanelTexts.add(txtTestado,"wrap");
		PanelTexts.add(new JLabel("Estados não terminais - Ex: A,B,C"));
		PanelTexts.add(txtNTestado,"wrap");
		PanelTexts.add(new JLabel("Regras de produção - Ex: 1c1, 1a2, 2b3"));
		PanelTexts.add(txtrProducao,"wrap");
		PanelTexts.add(new JLabel("Estado iniciaL - Ex: 'S'"));
		PanelTexts.add(txtiEstado,"wrap");
		PanelTexts.add(new JLabel("Entrada - Ex aaabbcc "));
		PanelTexts.add(textInput,"wrap");
		
		newValue = new JButton("Nova gramática");
		PanelTexts.add(newValue,"grow");
		newValue.addActionListener(this);
		verificar = new JButton("Validar Gramática");
		PanelTexts.add(verificar);
		verificar.addActionListener(this);
		editar = new JButton("Editar");
		PanelTexts.add(editar);
		editar.addActionListener(this);
		editar.setEnabled(false);

		EditarJanela();
		
		DesabilitarCampos();
		this.add(PanelTexts);
	}
	
	public void SetarValores(){
		gramatica = new Grammar();
		
		try {
			gramatica.setTestado(txtTestado.getText());  
			gramatica.setNTestado(txtNTestado.getText());
			gramatica.setRegrasProducao(txtrProducao.getText());
			gramatica.setiEstado(txtiEstado.getText());
		} catch (InvalidGrammarException e) {
			JOptionPane.showMessageDialog(null, e.getErro());
		} 	
	}
	
	public void EditarJanela(){
		setContentPane(new JPanel());
		setLayout(null);
		setVisible(true);
		setMinimumSize(new Dimension(450,200));
		setLocationRelativeTo(null);
	}
	
	public void habilitarCampos(){
		txtTestado.setEditable(true);
		txtNTestado.setEditable(true);
		txtrProducao.setEditable(true);
		txtiEstado.setEditable(true);
		textInput.setEditable(true);
	}
	
	
	public void DesabilitarCampos(){
		txtTestado.setEditable(false);;
		txtNTestado.setEditable(false);
		txtrProducao.setEditable(false);
		txtiEstado.setEditable(false);
		textInput.setEditable(false);
	}
	

	public void LimparTudo() {
        txtTestado.setText("");
		txtNTestado.setText("");
		txtrProducao.setText("");
		txtiEstado.setText("");
		textInput.setText("");
        gramatica = null;
    }  
	
	public void validar(){
		JOptionPane.showMessageDialog(null,gramatica.input(textInput.getText()) ? "\t Valido!" : "\t Invalido!");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			if(e.getSource() == newValue){
				LimparTudo();
				editar.setEnabled(true);
				//verificar.setEnabled(false);
				habilitarCampos();
			} else if(e.getSource() == verificar){
				SetarValores();
				DesabilitarCampos();
				validar();
			} else if(e.getSource() == editar){
				habilitarCampos();
			}
	}

}
