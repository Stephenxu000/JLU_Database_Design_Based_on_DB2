# JLU_Database_Design_Based_on_DB2
基于DB2与JavaGUI的吉林大学数据库课设

项目时间：2020年11月份~2021年1月份终止

## 前言
由于课程时间与考试周有点冲突，未能实现同步在Github上的更新。

现将此课程项目梳理一下，与大家学习交流。

本课程是基于JAVA语言实现（课程要求）的，在基于JAVA GUI 编写对DB2数据库的相关操作。

文本档内容为 数据库精要代码+效果展示

数据库：实验室DB2的sample表

实验环境：实验室Windows XP 虚拟机

任务1-4、9、11为完善老师代码，不是我的代码，不上传于GitHub。

这里只上传我的GUI代码，谢谢理解。

由于是初学GUI，自动布局啥的也没做到，该课程主要还是对数据库的实验课，多多包涵。

## 一、数据库精要代码（背）

相关操作如下：

### 连接DB2数据库

```java
Class.forname("COM.ibm.db2.jdbc.app.DB2Driver");//加载JDBC驱动
String url = "jdbc:db2:sample";//欲连接的数据库路径
String userid = "db2admin";
String passwd = "db2admin";
Connection conn=DriverManager.getConnection(url,userid,passwd);
```

### 数据库执行sql语句操作

#### 插入sql

```java
//3.1单行插入  多行插入循环即可
String sql="INSERT INTO TEMPL(EMPNO,FIRSTNAME,LASTNAME,EDLEVEL) values (?,?,?,?)";
PreparedStatement pstmt = conn.prepareStatement(sql);//创建ps语句
pstmt.setString(1,"000010");//设置问号的值
/*
...（设置？的值）
*/
int rows=pstmt.executeUpdate();//执行sql语句，返回更新行数
sample.commit();
```

#### 更新sql

```java
Statement updStmt = con.createStatement();
sql="UPDATE TEMPL" + "SET LASTNAME = 'Stohl'" + "WHERE EMPNO = '000110'")
int numRows = updStmt.executeUpdate{sql};
```

#### 删除sql（未实现GUI删除）

```java
sql="DELETE FROM TEMPL WHERE EMPNO=?";
pstmt = sample.prepareStatement(sql);
pstmt.setString(1,"100010");
deleteCount = pstmt.executeUpdate();
```

#### 查询sql

```java
 String sql = "select NAME,JOB,SALARY from staff Where ID = 10"
 ResultSet rs = stmt.executequery(sql);
 while(rs.next()){
	String data1 = rs.getString(1);//从1开始
   String data2 = rs.getString(2);
   String data3 = rs.getString(3);
   String data4 = rs.getString(4);
  }
```

 #### 子查询插入

```java
String name=Dantext_name.getText();//获取输入属性
String add=DanText_value.getText();//获取等于的值
sql="insert into TEMPL (EMPNO,...,...) select ...,COMM from JLU.EMPLOYEE where "+name+"=?";
```

  #### 修改行列的某个值

```java
int col=table.getSelectedCol();
int row=table.getSelectedRow();
String change_name = head[col];//得到属性名
String ID = (String)table.getValueAt(Select_row,0);//得到主键ID
String change_value = ChangeText.getText(); 
sql = "UPDATE TEMPL SET " + change_name + " = " +"'"+ change_value+"'" + " WHERE EMPNO" + " = "+ID;
```

 #### 创建滚动结果集

```java
ResultSet rs = null;
sql = "select NAME,JOB,SALARY from staff";
PrepareStatement stmt = sample.prepareStatement(sql,rs.TYPE_SCROLL_INSENSITIVE,rs.CONCUR_READ_ONLY)*//创建可滚动，只读结果集
ResultSet rs = stmt.executeQuery();
/*
rs.last();
rs.previous();
rs.first();
rs.next();
*/
```
 #### 批处理操作 

```java
Statement stmt = sample.createStatement();//连接
stmt.addBatch("INSERT INTO DB2ADMIN.DEPARTMENT"+"VALUES('BT6','BATCH6 NEWYORK','BBBBB1','BTT','NEW YORK CITY6')");
/*
... 同上sql
*/
int []updateCounts=stmt.executeBatch();
conn.commit();
```

  #### 取数据库结构

```java
DatabaseMetaData dbmd = sample.getMeteData();*//**获取数据库结构存储信息*
String []tableTypes={"TABLE","VIEW"};
ResultSet rs = dbmd.getTables(null,"UDBA","%",tableTypes);
```

#### blob类型对象读取

```java
//用SetBinaryStream(int n,java.io.InputStream x,int length)
Statement st=conn.createStatement();
ResultSet rs=st.executeQuery("select image from employee");
while(rs.next()){
  //读取Blob对象
  Blob blob = (Blob)rs.getBlob(1);
  //Blob对象转化为InputStream流
  InputStream inputStream = blob.getBinaryStream();
    //要写入文件
  File fileOutput = new File("e:/pic/pic_XXX.jpg");
    //文件的写入流定义*
  FileOutputStream fo = new FileOutputStream(fileOutput);
  int c;
  //读取流并写入到文件中
  while((c = inputStream.read())!=-1)
  fo.write(c);
  fo.close();
  }
```

#### 插入blob对象

```java
PreparedStatement preparedStatement = conn.prepareStatement("insert into emp_photo values('000130','jpeg',?)");
  //创建文件对象
  File file = new File("e:123.jpg");
  //创建流对象
  BufferedInputStream imageInput = new BufferedInputStream(
  new FileInputStream(file));
  //参数赋值
  preparedStatement.setBinaryStream(1,imageInput,(int) file,length());
  //第二个参数InputStream对象，第三个参数字节数int
  *//**执行语句
  preparedStatement.executeUpdate();
```

#### clob类型对象读取 截取其中某段内容

```java
Clob resumelob = null;//定义clob类型
sql1 = "SELECT POSSTR(RESUME,'Personal') "+"FROM JLU.EMP_RESUME "+ "WHERE EMPNO = ? AND RESUME_FORMAT = 'ascii' ";
sql2 = "SELECT POSSTR(RESUME,'Department') " + "FROM JLU.EMP_RESUME " + "WHERE EMPNO = ? AND RESUME_FORMAT = 'ascii' ";
sql3 = "SELECT EMPNO, RESUME_FORMAT, "+"SUBSTR(RESUME,1,?)||SUBSTR(RESUME,?) AS RESUME "+"FROM JLU.EMP_RESUME "+ "WHERE EMPNO = ? AND RESUME_FORMAT = 'ascii'";
stmt3 = conn.prepareStatement (sql3);
stmt3.setInt (1, startper1);
stmt3.setInt (2, startdpt);
stmt3.setString ( 3, empnum);
rs3 = stmt3.executeQuery();
while (rs3.next()) {
empno = rs3.getString(1);
resumefmt = rs3.getString(2);
resumelob = rs3.getClob(3);
long len = resumelob.length();
int len1 = (int)len;
String resumeout = resumelob.getSubString(1, len1);
System.out.println(resumeout);//输出
POSSTR(RESUME,'Personal')//查看Personal单词在resume位置
SUBSTR(RESUME,1,length)//取resume中1-length长度字段
```

### 异常处理，关闭对象资源

```java
if(rs != null){
    try{
    rs.close();
    }catch(SQLEXception e){
        e.printStackTrace();
    }
}
if(stmt != null){
    try{
        stmt.close();
    }catch(SQLException e){
        e.printStackTrace();
    }
}
if(conn != null){
    try{
        conn.close();
    }catch(SQLException e){
        e.printStackTrace();
    }
}
```

## 二、项目说明与演示



### 任务5：实现单行插入、多行插入、通过子查询插入功能。



### 任务6：修改讲结果集中空的列值均已“空”形式显示。



### 任务7：进行以对话框形式提示所有的违反约束，类型不匹配等操纵类错误。

此处我偷懒了，我直接把异常的错误信息全输出来了，这里可以判断返回的SQLSTATE进行分类提示处理。下面我把提示框的类型给例举一下吧

```java
//JOptionPane.INFORMATION_MESSAGE=-1 无提示信息 第四个参数能改成-1
JOptionPane.showMessageDialog(null, "我是默认框", "默认框",JOptionPane.DEFAULT_OPTION);
//=0 错误信息
JOptionPane.showMessageDialog(null, "我是错误框","错误框",JOptionPane.ERROR_MESSAGE);
//=1 提示信息
JOptionPane.showMessageDialog(null, "我是提示框", "提示框",JOptionPane.INFORMATION_MESSAGE);
//=2 警告信息
JOptionPane.showMessageDialog(null, "我是警告框", "警告框",JOptionPane.WARNING_MESSAGE);
//=3 问题信息
JOptionPane.showMessageDialog(null, "我是问题框", "问题框", JOptionPane.QUESTION_MESSAGE);
```

### 任务8：实现结果集中任意行、任意列的修改操作。

程序好像有点问题，要先按修改按钮才能启动鼠标事件监听器，再按动表格某个单元格，输入数据即可修改。

### 任务10：实现数据库中读取、插入图片功能。

忘记rs.close();重传图片会报错，因为CLOB、BLOB大对象循环结果集中不能重新执行数据库查询操作。