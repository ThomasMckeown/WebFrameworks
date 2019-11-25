
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package beans;
 
import javax.faces.bean.ManagedBean;
//session means that it will save the user data until they close the browser down
import javax.faces.bean.SessionScoped;
 
import java.sql.*;
import java.util.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
 
@ManagedBean(name="login_bean")
@SessionScoped
public class login_bean {
    @Size(min=3, max=25, message="You must enter a user name")
    private String username;
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message="Must enter a password at least a length of 8 with a capital letter and number")
    private String password;
    private String dbusername;
    private int admin;
    
    public String getDbpassword() {
        return dbpassword;
    }
    public String getDbusername() {
        return dbusername;
    }
    public int getDbadmin()
    {
        return dbadmin;
    }
    
    private String dbpassword;
    private int dbadmin;
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
            dbadmin=rs.getInt(6);
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
    public int getAdmin()
    {
        return admin;
    }
    public void setAdmin(int admin)
    {
        this.admin = admin;
    }
                            
    public String checkValidUser()
    {
        dbData(username);
 
        if(username.equalsIgnoreCase(dbusername))
        {
 
            if(password.equals(dbpassword))
            {
                return "valid";
            }
            
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
    public String checkValidAdminUser()
    {
        dbData(username);
        
 
        if(username.equalsIgnoreCase(dbusername))
        {
 
            if(password.equals(dbpassword) && dbadmin == 1)
            {
                return "valid";
            }
            
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



