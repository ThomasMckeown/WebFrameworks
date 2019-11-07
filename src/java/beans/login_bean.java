
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package beans;
 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
 
import java.sql.*;
import java.util.*;
 
@ManagedBean(name="login_bean")
@RequestScoped
public class login_bean {
    private String username;
    private String password;
    private String dbusername;
 
    public String getDbpassword() {
        return dbpassword;
    }
    public String getDbusername() {
        return dbusername;
    }
 
    private String dbpassword;
    Connection con;
    Statement ps;
    ResultSet rs;
    String SQL_Str;
 
    public void dbData(String UName)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/books","root","");
            ps = con.createStatement();
            SQL_Str="Select * from users where username like ('" + UName +"')";
            rs=ps.executeQuery(SQL_Str);
            rs.next();
            dbusername=rs.getString(2).toString();
            dbpassword=rs.getString(3).toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Exception Occur :" + ex);
        }
    }
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
    public String checkValidUser()
    {
        dbData(username);
 
        if(username.equalsIgnoreCase(dbusername))
        {
 
            if(password.equals(dbpassword))
                return "valid";
            else
            {
                return "invalid";
            }
        }
        else
        {
            return "invalid";
        }
    }
}



