package manager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.imageio.plugins.bmp.BMPImageWriteParam;
import javax.sql.rowset.WebRowSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import connect.model;

public class View extends JPanel implements ActionListener {
	JComboBox comArea, comJong, comsearch; 
	JTextField tadName, taddres, tfoodna, tprice;
	JButton bInsert,bModify,bDelete;
	JTextField tsearch;
	JTable table;
	JButton cbtn;
	JLabel piclabel;
	
	ManagerModel m_model;
	model c_model;
	
	File f=null;
	String fName="";
	
	
	public View() {
		Layout();
		event();
		ConnectDB();
	}
	public void ConnectDB() {
		try {
			c_model = new model();
			System.out.println("���� ���Ἲ��");
		} catch (Exception e) {
			System.out.println("���� �������");
			e.printStackTrace();
		} 
	}

	private void Layout() {
		//ȭ�� ����
		String[] area = {"����", "õȣ","�浿"};
		comArea = new JComboBox(area);
		String[] jong = {"�߽�", "���", "�ѽ�"};
		comJong = new JComboBox(jong);
		String[] seach= {"����","����","�����","�����̸�"};
		comsearch = new JComboBox(seach);
		tadName= new JTextField(10);
		taddres= new JTextField(10);
		tfoodna= new JTextField(10);
		tprice= new JTextField(10);
		bInsert= new JButton("�߰�");
		bModify= new JButton("����");
		bDelete= new JButton("����");
		tsearch = new JTextField(10);
		
		cbtn = new JButton("���� ã��");
		
		
		m_model = new ManagerModel(); 
		table= new JTable(m_model);
		table.setModel(m_model);
		
		
		
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new GridLayout(2,0));
		
		JPanel northpanel = new JPanel();
		northpanel.setLayout(new BorderLayout());
		northpanel.setBorder(new TitledBorder("�����Է�"));
		
		JPanel northpanel_north = new JPanel();
		northpanel_north.setLayout(new BorderLayout());
		
		JPanel northpanel_northwest = new JPanel();
		northpanel_northwest.setLayout(new GridLayout(0, 2));
		northpanel_northwest.add(new JLabel("����"));
		northpanel_northwest.add(comArea);
		northpanel_northwest.add(new JLabel("����"));
		northpanel_northwest.add(comJong);
		northpanel_northwest.add(new JLabel("�����"));
		northpanel_northwest.add(tadName);
		northpanel_northwest.add(new JLabel("�ּ�"));
		northpanel_northwest.add(taddres);
		northpanel_northwest.add(new JLabel("�����̸�"));
		northpanel_northwest.add(tfoodna);
		northpanel_northwest.add(new JLabel("����"));
		northpanel_northwest.add(tprice);
		
		JPanel northpanel_northeast = new JPanel();
		northpanel_northeast.setBorder(new TitledBorder("���� �̹���"));
		northpanel_northeast.setLayout(new BorderLayout());
		piclabel = new JLabel();
		
		northpanel_northeast.add(piclabel,BorderLayout.CENTER);
		northpanel_northeast.add(cbtn,BorderLayout.SOUTH);
		
		JPanel northpanel_button = new JPanel();
		northpanel_button.setLayout(new GridLayout(1, 3));
		northpanel_button.add(bInsert);
		northpanel_button.add(bModify);
		northpanel_button.add(bDelete);
		
		
		northpanel_north.add(northpanel_northwest,BorderLayout.NORTH);
		northpanel_north.add(northpanel_northeast,BorderLayout.CENTER);
		northpanel_north.add(northpanel_button,BorderLayout.SOUTH);
		
		northpanel.add(northpanel_north);
		
		JPanel southpanel =new JPanel();
		southpanel.setLayout(new BorderLayout());
		southpanel.setBorder(new TitledBorder("�˻�"));
		
		JPanel southpanel_north = new JPanel();
		southpanel_north.setLayout(new GridLayout(1, 3));
		southpanel_north.add(new JLabel("�˻�â"));
		southpanel_north.add(comsearch);
		southpanel_north.add(tsearch);
		
		JPanel southpanel_south = new JPanel();
		southpanel_south.add(new JScrollPane(table),BorderLayout.CENTER);
		
		southpanel.add(southpanel_north,BorderLayout.NORTH);
		southpanel.add(southpanel_south,BorderLayout.SOUTH);
		
		northpanel.setLayout(new GridLayout(1,1));
		mainpanel.add(northpanel);
		mainpanel.add(southpanel);
		
		add(mainpanel);
		
		bInsert.addActionListener(this);
		bModify.addActionListener(this);
		bDelete.addActionListener(this);
		tsearch.addActionListener(this);
		cbtn.addActionListener(this);
		
		
	}
	
	private void event() {
		
		
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
				int row=table.getSelectedRow();
				int col=4;
				
				String data= (String)table.getValueAt(row, col);
				try {
					
					Save sa=c_model.selectbypk(data);
					selectbypk(sa);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}});
	}
	
	class ManagerModel extends AbstractTableModel{
		
		ArrayList data=new ArrayList();
		String[] Colum = {"����","����","�����","�ּ�","�����̸�","����"};

		@Override
		public int getColumnCount() {
			return Colum.length;
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			ArrayList temp= (ArrayList) data.get(row);
			return temp.get(col);
		}
		public String getColumnName (int col) {
			return Colum[col];
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object evt=e.getSource();
		
		if (evt==bInsert) {
			fName=System.currentTimeMillis()+f.getName();
			Insert(fName);
			fileSave(f,".//upload2",fName);
			
			
		}else if (evt==bModify) {
			Modify();
		}else if (evt==tsearch) {
			Search();
		}else if (evt==bDelete) {
			Delete();
		}else if (evt==cbtn) {
			JFileChooser jc=new JFileChooser();
			jc.showOpenDialog(this);
			f=jc.getSelectedFile();
			
			
		}
		
	}

	void fileSave(File file, String path, String name) {
		
		try {
			File f=new File(path);
			if (!f.exists()) {
				f.mkdirs();//���������
			}
			String filePath=path+"\\"+name;
			
			FileInputStream fis=new FileInputStream(file);
			FileOutputStream fos=new FileOutputStream(filePath);
			
			int i=0;
			byte[] buffer=new byte[1024];
			
			while ((i=fis.read(buffer,0,1024))!=-1) {
				fos.write(buffer,0,i);
			}
			fis.close();
			fos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void Delete() {
		String str=tfoodna.getText();
		
		try {
			c_model.Delete(str);
			JOptionPane.showMessageDialog(null, "�����Ϸ�");
			tadName.setText(null);
			taddres.setText(null);
			tfoodna.setText(null);
			tprice.setText(null);
			piclabel.setIcon(null);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "��������");
			e1.printStackTrace();
		}
	}

	private void Search() {
		int idx=comsearch.getSelectedIndex();
		String str=tsearch.getText();
		
		try {
			ArrayList data=c_model.Search(str, idx);

			m_model.data=data;
			table.setModel(m_model);
			m_model.fireTableDataChanged();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	ImageIcon icon;
	
	void selectbypk(Save sa) {
		comArea.setSelectedItem(sa.getArea());
		comJong.setSelectedItem(sa.getJong());
		tadName.setText(sa.getAdname());
		taddres.setText(sa.getAddres());
		tfoodna.setText(sa.getFoodna());
		tprice.setText(String.valueOf(sa.getPrice()));
		
		System.out.println(sa.getImg());
		
		icon=new ImageIcon(".\\upload2\\"+sa.getImg());
		piclabel.setIcon(icon);
		ImageIcon newIcon;
		Image image=icon.getImage();
		image.getScaledInstance(piclabel.getWidth(), piclabel.getHeight(),0);
		int imgW=piclabel.getWidth();
		int imgH=piclabel.getHeight();
		Image img=icon.getImage();
		Image newimg=img.getScaledInstance(imgW, imgH, java.awt.Image.SCALE_SMOOTH);
		newIcon=new ImageIcon(newimg);
		piclabel.setIcon(newIcon);
		
	}

	private void Modify() {
		Save sa=new Save();
		String str=tfoodna.getText();
		sa.setArea((String)comArea.getSelectedItem());
		sa.setJong((String)comJong.getSelectedItem());
		sa.setAdname(tadName.getText());
		sa.setAddres(taddres.getText());
		sa.setFoodna(tfoodna.getText());
		sa.setPrice(Integer.parseInt(tprice.getText()));
		
		try {
			c_model.Modeify(sa);
			JOptionPane.showMessageDialog(null, "�����Ϸ�");
			tadName.setText(null);
			taddres.setText(null);
			tfoodna.setText(null);
			tprice.setText(null);
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "��������");
			e1.printStackTrace();
		}
	}

	private void Insert(String fName) {
		Save sa=new Save();
		sa.setArea((String) comArea.getSelectedItem());
		sa.setJong((String) comJong.getSelectedItem());
		sa.setAdname(tadName.getText());
		sa.setAddres(taddres.getText());
		sa.setFoodna(tfoodna.getText());
		sa.setPrice(Integer.parseInt(tprice.getText()));
		
		sa.setImg(fName);
		
		try {
			System.out.println("�߰�����");
			c_model.Insert(sa);
			JOptionPane.showMessageDialog(null, "�߰��Ϸ�");
			
			tadName.setText(null);
			taddres.setText(null);
			tfoodna.setText(null);
			tprice.setText(null);
			piclabel.setIcon(null);
			
		} catch (Exception e1) {
			System.out.println("�߰� ����");
			JOptionPane.showMessageDialog(null, "�߰�����");
			e1.printStackTrace();
		}
	}
	
	

}
