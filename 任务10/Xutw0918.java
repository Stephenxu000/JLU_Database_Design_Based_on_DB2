import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

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
public class Xutw0918
{
private JFrame frame = new JFrame("09181224 徐天文 picture");

//创建e/output文件夹
	boolean createDir(String destDirName) {  
      File dir = new File(destDirName);  
      if (dir.exists()) {  
          System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");  
          return false;  
      }  
      if (!destDirName.endsWith(File.separator)) {  
          destDirName = destDirName + File.separator;  
      }  
      //创建目录  
      if (dir.mkdirs()) {  
          System.out.println("创建目录" + destDirName + "成功！");  
          return true;  
      } else {  
          System.out.println("创建目录" + destDirName + "失败！");  
          return false;  
      }  
	}
private void createConnection()//建立DB2数据库连接，同时显示页面
{
	frame.setBounds(100,100,300,200);
	frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
	JButton jb_quiry = new JButton("查询");
	JButton jb_insert = new JButton("插入");
	frame.getContentPane().add(jb_quiry);
	frame.getContentPane().add(jb_insert);
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	jb_quiry.addActionListener(new ActionListener() {//查询按钮
		public void actionPerformed(ActionEvent e) {
			function1();
		}
	});
	jb_insert.addActionListener(new ActionListener() {//插入按钮
		public void actionPerformed(ActionEvent e) {
			function2();
		}
	});
}

private void function1()//查询页面
{
	createDir("e:/output_pic");
	JFrame jf = new JFrame("查询");
	jf.setBounds(100,100,300,150);
	jf.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
	JLabel jl = new JLabel("您想要查询的编号：");
	final JTextField jt = new JTextField(10);
	JButton jb = new JButton("确认");
	jf.getContentPane().add(jl);
	jf.getContentPane().add(jt);
	jf.getContentPane().add(jb);
	jf.getContentPane().add(new JLabel("请注意：查询结果图片将会输出到e:/output_pic文件夹中..."));
	jf.setVisible(true);
	jb.addActionListener(new ActionListener() {//查询按钮
		public void actionPerformed(ActionEvent e) {
			String number = jt.getText();
			try{
				Connection conn = null;
			    PreparedStatement ps = null;
				String sql = "select picture from JLU.emp_photo where empno=?";
				conn = DbUtils.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, number);
				ResultSet rs = ps.executeQuery();
				rs.next();
				Blob blob = rs.getBlob(1);
				InputStream inputStream = blob.getBinaryStream();
				File fileOutput = new File("e:/output_pic/output_"+number+".jpg");
				FileOutputStream fo = new FileOutputStream(fileOutput);
				int c;
				while((c = inputStream.read()) != -1)
					fo.write(c);
				fo.close();
				JOptionPane.showMessageDialog(null,"图片已经输出到e:/output_pic文件夹中！");
				rs.close();
			} catch(Exception ee) {
				System.out.println("查询错误");
				JOptionPane.showMessageDialog(null,ee,"错误",JOptionPane.WARNING_MESSAGE);
				ee.printStackTrace();
			}
		}
	});
}

private void function2()//插入页面
{
	JFrame frame = new JFrame("插入");
	frame.setBounds(100,100,400,200);
	frame.getContentPane().setLayout(new GridLayout(3,1));
	JPanel jp_ID = new JPanel();
	JLabel IDlabel = new JLabel("请输入员工编号");
	final JTextField jt_ID = new JTextField(10);
	jp_ID.add(jt_ID);
	jp_ID.add(IDlabel);
	JPanel jp_path = new JPanel();
	JLabel jl_path = new JLabel("想要添加的图片的路径");
	final JTextField jt_path = new JTextField(20);
	jp_path.add(jl_path);jp_path.add(jt_path);
	JPanel jp_ok = new JPanel();
	JButton jb_ok = new JButton("确认");
	jp_ok.add(jb_ok);
	frame.getContentPane().add(jp_ID);
	frame.getContentPane().add(jp_path);
	frame.getContentPane().add(jp_ok);
	frame.setVisible(true);
	jb_ok.addActionListener(new ActionListener() {//插入按钮
		public void actionPerformed(ActionEvent e) {
			String number = jt_ID.getText();
			String path = jt_path.getText();
			try{
				String sql = "insert into JLU.emp_photo values(?,'bitmap',?)";
				Connection conn = null;
			    PreparedStatement ps = null;
				conn = DbUtils.getConnection();
				ps = conn.prepareStatement(sql);
				File file = new File(path);
				BufferedInputStream imageInput = new BufferedInputStream(new FileInputStream(file));
				ps.setString(1, number);
				ps.setBinaryStream(2, imageInput,(int) file.length());
				ps.executeUpdate();
				conn.commit();
				JOptionPane.showMessageDialog(null,"添加成功");
			} catch(SQLException ee) {
				System.out.println("添加错误");
				System.out.println(ee.getMessage());
				System.out.println("1");
				System.out.println(ee.getSQLState());
				System.out.println("2");
				System.out.println(ee.getErrorCode());
				System.out.println("3");
				JOptionPane.showMessageDialog(null,ee,"错误",JOptionPane.WARNING_MESSAGE);
				ee.printStackTrace();
			} catch (Exception eee){
				System.out.println("添加错误");
				JOptionPane.showMessageDialog(null,eee,"错误",JOptionPane.WARNING_MESSAGE);
			}
		}
	});
	
}

public static void main( String args[]) throws Exception
{
	Xutw0918 l10 = new Xutw0918();
	l10.createConnection();
	System.out.println("end of main");

}//End of main
}

