import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.sql.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;


class MyObject{
	private String EMPNO,FIRSTNME,MIDINIT,LASTNAME,WORKDEPT,PHONENO,HREDATE,JOB,EDLEVEL,SEX,BIRTHDATE,SALARY,BONUS,COMM;
	//private float  SALARY,BONUS,COMM;
	public String getEMPNO() {
		return EMPNO;
	}
	public void setEMPNO(String eMPNO) {
		EMPNO = eMPNO;
	}
	public String getLASTNAME() {
		return LASTNAME;
	}
	public void setLASTNAME(String lASTNAME) {
		LASTNAME = lASTNAME;
	}
	public String getWORKDEPT() {
		return WORKDEPT;
	}
	public void setWORKDEPT(String wORKDEPT) {
		WORKDEPT = wORKDEPT;
	}
	public String getPHONENO() {
		return PHONENO;
	}
	public void setPHONENO(String pHONENO) {
		PHONENO = pHONENO;
	}
	public String getHREDATE() {
		return HREDATE;
	}
	public void setHREDATE(String hREDATE) {
		HREDATE = hREDATE;
	}
	public String getJOB() {
		return JOB;
	}
	public void setJOB(String jOB) {
		JOB = jOB;
	}
	public String getEDLEVEL() {
		return EDLEVEL;
	}
	public void setEDLEVEL(String eDLEVEL) {
		EDLEVEL = eDLEVEL;
	}
	public String getSEX() {
		return SEX;
	}
	public void setSEX(String sEX) {
		SEX = sEX;
	}
	public String getBIRTHDATE() {
		return BIRTHDATE;
	}
	public void setBIRTHDATE(String bIRTHDATE) {
		BIRTHDATE = bIRTHDATE;
	}
	public String getSALARY() {
		return SALARY;
	}
	public void setSALARY(String sALARY) {
		SALARY = sALARY;
	}
	public String getBONUS() {
		return BONUS;
	}
	public void setBONUS(String bONUS) {
		BONUS = bONUS;
	}
	public String getCOMM() {
		return COMM;
	}
	public void setCOMM(String cOMM) {
		COMM = cOMM;
	}
	public String getFIRSTNME() {
		return FIRSTNME;
	}
	public void setFIRSTNME(String fIRSTNME) {
		FIRSTNME = fIRSTNME;
	}
	public String getMIDINIT() {
		return MIDINIT;
	}
	public void setMIDINIT(String mIDINIT) {
		MIDINIT = mIDINIT;
	}
	
}
class MyObjectDao{
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    public List<MyObject> queryALLMyObject(){
    	//查询所有对象
    	String sql = "select * from TEMPL";
    	List<MyObject> list= new ArrayList<MyObject>();
    	try {
    		 conn = DbUtils.getConnection();
             ps = conn.prepareStatement(sql);
             rs = ps.executeQuery();
             System.out.println(ps.toString());
             while (rs.next()) {
            	 
                 MyObject myobject = new MyObject();
                 myobject.setEMPNO(rs.getString(1));
                 myobject.setFIRSTNME(rs.getString(2));
                 myobject.setMIDINIT(rs.getString(3));
                 myobject.setLASTNAME(rs.getString(4));
                 myobject.setWORKDEPT(rs.getString(5));
                 myobject.setPHONENO(rs.getString(6));
                 myobject.setHREDATE(rs.getString(7));
                 myobject.setJOB(rs.getString(8));
                 myobject.setEDLEVEL(rs.getString(9));
                 myobject.setSEX(rs.getString(10));
                 myobject.setBIRTHDATE(rs.getString(11));
                 myobject.setSALARY(rs.getString(12));
                 myobject.setBONUS(rs.getString(13));
                 myobject.setCOMM(rs.getString(14));

                 list.add(myobject);
    	}
    }catch (SQLException e) {
    	e.printStackTrace();
    }
    	return list;
    }
}
class DbUtils {
    
    private  static  final String URL="jdbc:db2:sample";              
    private  static  final String USER="db2admin";
    private  static  final String PASSWORD="db2admin";//此处为数据库密码，更改为自己数据库的密码
    
    static{
          try {  
            Class.forName("COM.ibm.db2.jdbc.app.DB2Driver");
        } catch (Exception e) {
            System.out.println("\n  Error loading DB2 Driver...\n");
            System.out.println(e);
            System.exit(1);
        }
    }
        
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL);
    }
    
    //关闭方法
    public  static void close(ResultSet rs, Statement stat, Connection conn) throws SQLException{
        if(rs!=null){
            rs.close();
        }if(stat!=null){
            stat.close();
        }if(conn!=null){
            conn.close();
        }
    }       
}

public class Xutw0918 extends JFrame implements ActionListener {
	
	 private JPanel contentPane;
	    private JTable table;
	    private String head[]=null;
	    private Object [][]data=null;
	    private MyObjectDao myobject=new MyObjectDao();
	    JButton putinS=new JButton("单行插入");
		JButton putinDuo=new JButton("多行插入");
		JButton putinDan=new JButton("子查询插入");
	    
		public DefaultTableModel tableModel;
		
		//暂时使用文本框读入数据
		JTextField SingleText=new JTextField();//单行插入
		JTextArea DuoText=new JTextArea();//多行插入
		JTextField DanText=new JTextField();//子查询插入
	    /**
	     * Launch the application.
	     */
		 public static void main(String[] args) {
		        EventQueue.invokeLater(new Runnable() {
		            public void run() {
		                try {
		                    Xutw0918 frame = new Xutw0918();
		                    frame.setVisible(true);
		                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		                } catch (Exception e) {
		                    e.printStackTrace();
		                }
		            }
		        });
		    }
	    /**
	     * Create the frame.
	     */
		Container frame;
		double windowWidth=1000; //获得窗口宽
		double windowHeight=800; //获得窗口高
	    public Xutw0918() {
	    	
	    	super("09181224-徐天文");
	    	
	    	
	    	 table = new JTable();
	         table.setBorder(new LineBorder(new Color(0, 0, 0)));
	         head=new String[] {
	             "EMEPO", "FIRSTNAME", "MIDINIT", "LASTNAME", "WORKDEPT", "PHONENO", "HIREDATE","JOB","EDLEVEL","SEX","BIRTHDATE","SALARY","BONUS","COMM"
	         };
	         tableModel=new DefaultTableModel(queryData(),head){
	             /**
	 			 * 
	 			 */
	 			private static final long serialVersionUID = 1L;

	 			public boolean isCellEditable(int row, int column)
	             {
	                 return false;
	             }
	         };
	        table.setModel(tableModel);
	        JScrollPane scrollPane = new JScrollPane(table);//为JTable a建立一个区域
	 	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//显示水平滑动栏
	 	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//显示竖直滑动栏
	 	    
	 	    table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
	        FitTableColumns(table);
	        scrollPane.setViewportView(table);
	        
	        
	        frame=getContentPane();
	        frame.setLayout(null);//自由布局
	        
	        //多行插入页面
	        JScrollPane Duotext_ScrollPane=new JScrollPane();//文本域滚动条
	        Duotext_ScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        Duotext_ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);//水平和垂直滚动条自动出现
	        //略
	        
	        
	        DuoText.setLineWrap(true);    //设置文本域中的文本为自动换行
	        frame.add(scrollPane);
	        frame.add(putinS);
	        frame.add(putinDuo);
	        frame.add(putinDan);
	        frame.add(SingleText);
	        frame.add(DuoText);
	        frame.add(DanText);
	        frame.add(Duotext_ScrollPane);
	        //设置监听器
	        putinS.addActionListener(this);
	        putinDuo.addActionListener(this);
	        putinDan.addActionListener(this);
	        //b
	        contentPane = new JPanel(); 
	        
	      //布局
	        //putinDuo.setBounds(50,30,100,30);
			//putinDan.setBounds(800,30,100,30);
			
			SingleText.setBounds(50,(int)windowHeight-310 , 700, 30);
			putinS.setBounds(800,(int)windowHeight-310,100,30);
			
			DuoText.setBounds(50,(int)windowHeight-255 , 700, 100);
			Duotext_ScrollPane.setBounds(50,(int)windowHeight-255 , 700, 100);
			Duotext_ScrollPane.setViewportView(DuoText);
			putinDuo.setBounds(800,(int)windowHeight-220,100,30);
			
			DanText.setBounds(50,(int)windowHeight-140,700,30);
			putinDan.setBounds(800,(int)windowHeight-140,100,30);
			
			scrollPane.setBounds(50,75,(int)windowWidth-125,(int)windowHeight-400);
			
			
			frame.add(contentPane);
			setSize((int)windowWidth,(int)windowHeight);
			//windowWidth = this.getWidth(); //获得窗口宽
	        //windowHeight = this.getHeight(); //获得窗口高
			setVisible(true);
			

	    }
	    
	    //生成表格数据
	    /**
	     * @return
	     */
	    public Object[][] queryData(){
	        List<MyObject> list=myobject.queryALLMyObject();
	        data=new Object[list.size()][head.length];

	    

	        for(int i=0;i<list.size();i++){
	            for(int j=0;j<head.length;j++){
	                data[i][0]=list.get(i).getEMPNO();
	                data[i][1]=list.get(i).getFIRSTNME();
	                data[i][2]=list.get(i).getMIDINIT();
	                data[i][3]=list.get(i).getLASTNAME();
	                data[i][4]=list.get(i).getWORKDEPT();
	                data[i][5]=list.get(i).getPHONENO();
	                data[i][6]=list.get(i).getHREDATE();
	                data[i][7]=list.get(i).getJOB();
	                data[i][8]=list.get(i).getEDLEVEL();
	                data[i][9]=list.get(i).getSEX();
	                data[i][10]=list.get(i).getBIRTHDATE();
	                data[i][11]=list.get(i).getSALARY();
	                data[i][12]=list.get(i).getBONUS();
	                data[i][13]=list.get(i).getCOMM();
	            }
	        }
	        return data;
	    }
	    void FitTableColumns(JTable myTable) {               //设置列表宽度随内容调整

	        JTableHeader header = myTable.getTableHeader();

	        int rowCount = myTable.getRowCount();

	        Enumeration columns = myTable.getColumnModel().getColumns();

	        while (columns.hasMoreElements()) {
	            TableColumn column = (TableColumn) columns.nextElement();

	            int col = header.getColumnModel().getColumnIndex(

	                    column.getIdentifier());

	            int width = (int) myTable.getTableHeader().getDefaultRenderer()

	                    .getTableCellRendererComponent(myTable,

	                            column.getIdentifier(), false, false, -1, col)

	                    .getPreferredSize().getWidth();

	            for (int row = 0; row < rowCount; row++){
	                int preferedWidth = (int) myTable.getCellRenderer(row, col)

	                        .getTableCellRendererComponent(myTable,

	                                myTable.getValueAt(row, col), false, false,

	                                row, col).getPreferredSize().getWidth();

	                width = Math.max(width, preferedWidth);

	            }

	            header.setResizingColumn(column);

	            column.setWidth(5+width + myTable.getIntercellSpacing().width);

	        }

	    }
	    void setobject(String sub[], PreparedStatement ps) {
	    	try { 
	    	for(int i=0;i<6;i++) {
	            	ps.setString(i+1, sub[i]);
	            }
	            ps.setDate(7, Date.valueOf(sub[6]));
	            ps.setString(8, sub[7]);
	            ps.setShort(9, Short.valueOf(sub[8]));
	            ps.setString(10, sub[9]);
	            ps.setDate(11, Date.valueOf(sub[10]));
	            ps.setBigDecimal(12, BigDecimal.valueOf(Double.valueOf((sub[11]))));
	            ps.setBigDecimal(13, BigDecimal.valueOf(Double.valueOf((sub[12]))));
	            ps.setBigDecimal(14, BigDecimal.valueOf(Double.valueOf((sub[13]))));
	            }
	    	catch(Exception x) {
	    		System.out.println(x.getMessage());
	    	}
	    }
	    public void actionPerformed(ActionEvent e) {
	    	Connection conn = null;
	        PreparedStatement ps = null;
	        PreparedStatement ps_data = null;
	        ResultSet rs = null;
	        //ResultSet rs = null;
			if(e.getSource()==putinS) {
				try {
					String sql="insert into TEMPL (EMPNO,FIRSTNME,MIDINIT,LASTNAME, WORKDEPT, PHONENO, HIREDATE,JOB,EDLEVEL,SEX,BIRTHDATE,SALARY,BONUS,COMM) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					conn = DbUtils.getConnection();
		            ps = conn.prepareStatement(sql);
		            //获取输入的数据
		            String add=SingleText.getText();
		            //String EMPNO="",FIRSTNME="",MIDINIT="",LASTNAME="",WORKDEPT="",PHONENO="",HREDATE="",JOB="",EDLEVEL="",SEX="",BIRTHDATE="",SALARY="",BONUS="",COMM="";
		            String[] sub=add.split("\\s+");//数据清理
		            if(sub.length>14)
		            {
		            	System.out.println("输入不符合格式 重新输入");
		            	return;
		            }
			    setobject(sub,ps);
		            ps.executeUpdate();	
		            tableModel.addRow(sub);
		            FitTableColumns(table);
		            conn.commit();
				}
				catch(Exception x) {
					System.out.println(x.getMessage());
				}
			}
			if(e.getSource()==putinDuo) {
				try {
					int num=0;
					conn = DbUtils.getConnection();
		            //获取输入的数据
		            String add=DuoText.getText();
		            //String EMPNO="",FIRSTNME="",MIDINIT="",LASTNAME="",WORKDEPT="",PHONENO="",HREDATE="",JOB="",EDLEVEL="",SEX="",BIRTHDATE="",SALARY="",BONUS="",COMM="";
		            String[] sub=add.split("\\s+");//数据清理
		            if(sub.length % 14!=0||sub.length==0)
		            {
				System.out.println(sub.length);
		            	System.out.println("输入不符合格式 重新输入");
		            	return;
		            }
		            else {
		            	num=sub.length/14;
		            }
		            int number=0;
		            while(number <num) {
		            	String addSingle[]=new String[14];
		            	int Singlenum=0;
		            	for(int i=number*14;i<(number+1)*14;i++) {
		            		addSingle[Singlenum++]=sub[i];
		            	}
		            	String sql="insert into TEMPL (EMPNO,FIRSTNME,MIDINIT,LASTNAME, WORKDEPT, PHONENO, HIREDATE,JOB,EDLEVEL,SEX,BIRTHDATE,SALARY,BONUS,COMM) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			            ps = conn.prepareStatement(sql);
			            setobject(addSingle,ps);
			            ps.executeUpdate();	
			            tableModel.addRow(addSingle);
			            FitTableColumns(table);
			            conn.commit();
			            number++;
		            }
				}
				catch(Exception x) {
					System.out.println(x.getMessage());
				}
			}
			if(e.getSource()==putinDan) {
				try {
					String sql="insert into TEMPL (EMPNO,FIRSTNME,MIDINIT,LASTNAME, WORKDEPT, PHONENO, HIREDATE,JOB,EDLEVEL,SEX,BIRTHDATE,SALARY,BONUS,COMM) select EMPNO, FIRSTNME,MIDINIT,LASTNAME, WORKDEPT, PHONENO, HIREDATE,JOB,EDLEVEL,SEX,BIRTHDATE,SALARY,BONUS,COMM from JLU.EMPLOYEE where EMPNO=?";
					String sql_data="select * from JLU.EMPLOYEE where EMPNO=?";
					conn = DbUtils.getConnection();
		            ps = conn.prepareStatement(sql);
		            ps_data=conn.prepareStatement(sql_data);
		            //获取输入的数据
		            String add=DanText.getText();
		            //String EMPNO="",FIRSTNME="",MIDINIT="",LASTNAME="",WORKDEPT="",PHONENO="",HREDATE="",JOB="",EDLEVEL="",SEX="",BIRTHDATE="",SALARY="",BONUS="",COMM="";
		            String[] sub=add.split("\\s+");//数据清理
		            if(sub.length!=1)
		            {
		            	System.out.println("输入不符合格式 重新输入");
		            	return;
		            }
		            ps.setString(1, sub[0]);
		            ps.executeUpdate();
		            ps_data.setString(1,sub[0]);
		            rs=ps_data.executeQuery();
		            String [] adddata=new String[14];
		            rs.next();
		            for(int i=0;i<14;i++) {
		            	adddata[i]=rs.getString(i+1);
		            }
		            tableModel.addRow(adddata);
		            FitTableColumns(table);
		            conn.commit();
				}
				catch(Exception x) {
					System.out.println(x.getMessage());
				}
			}
		}
}
    

    
