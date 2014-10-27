using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Data.SqlClient;
using System.Configuration;

/// <summary>
/// Summary description for WebService
/// </summary>
[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
// [System.Web.Script.Services.ScriptService]
public class WebService : System.Web.Services.WebService
{

    public WebService()
    {

        //Uncomment the following line if using designed components 
        //InitializeComponent(); 
    }

    [WebMethod]
    public string queryTable(string last_name)
    {

        //Declare Connection by passing the connection string from the web config file
        SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["Database1"].ConnectionString);

        //Open the connection
        conn.Open();

        string first_name = "";
        string last_name1 = "";
        string age = "";
        string school_name = "";
        string major = "";
        string address = "";
        string phone = "";


        SqlCommand cmd = new SqlCommand("Select * From Table1 where last_name='" + last_name + "'", conn);
        SqlDataReader reader = cmd.ExecuteReader();
        while (reader.Read())
        {
            first_name = first_name + reader["first_name"].ToString();
            last_name1 = last_name1  + reader["last_name"].ToString();
            age = age  + reader["age"];
            school_name = school_name  + reader["school_name"].ToString();
            major = major  + reader["major"].ToString();
            address = address  + reader["address"].ToString();
            phone = phone  + reader["phone"].ToString();
           
            
        }
        reader.Close();
        //close the connection
        conn.Close();

        return "info: " + first_name + "," + last_name1 + "," + age + "," + school_name + "," + major + "," + address + "," + phone;

    }

    [WebMethod]
    public string insertTable(string first_name, string last_name, string age, string school_name, string major, string address, string phone)
    {
        //Declare Connection by passing the connection string from the web config file
        SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["Database1"].ConnectionString);

        //Open the connection
        conn.Open();

        //Declare the sql command
        SqlCommand cmd = new SqlCommand
            ("Insert into Table1(first_name,last_name,age,school_name,major,address,phone)values('" + first_name + "','" + last_name + "','" + age + "','" + school_name + "','" + major + "','" + address + "','" + phone + "')", conn);

        //Execute the insert query
        cmd.ExecuteNonQuery();
        cmd.Dispose();
        //close the connection
        conn.Close();

        return "Success insert student information";

    }

    [WebMethod]
    public string deleteTable(string last_name)
    {
        //Declare Connection by passing the connection string from the web config file
        SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["Database1"].ConnectionString);

        //Open the connection
        conn.Open();

        //Declare the sql command
        SqlCommand cmd = new SqlCommand("Delete From Table1 Where last_name= '" + last_name + "'", conn);

        //Execute the insert query
        cmd.ExecuteNonQuery();
        cmd.Dispose();
        //close the connection
        conn.Close();

        return "Success delete student information";
    }
}
