using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.Data.SqlClient;
using System.Configuration;

namespace RestServiceRemoteDb
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service1" in code, svc and config file together.
    public class Service1 : IService1
    {


        /// 
        /// Query age, company, position based on name
        ///

        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json, UriTemplate = "queryInfo/{username}")]

        public Person QueryInfo(string username)
        {
            //Declare Connection by passing the connection string from the web config file
            SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["dbString"].ConnectionString);

            //Open the connection
            conn.Open();

            string userN = "";
            string pw = "";
            string phoneN = "";
            string firstName = "";
            string lastName = "";
            string dob = "";

            //Declare the sql command
            SqlCommand cmd = new SqlCommand("Select * From testTable where username='" + username + "'", conn);
            SqlDataReader reader = cmd.ExecuteReader();
            while (reader.Read())
            {
            
                userN = userN + ";" + reader["username"].ToString();
                pw = pw + ";" + reader["password"].ToString();
                phoneN = phoneN + ";" + reader["phone"];
                firstName = firstName + ";" + reader["firstname"];
                lastName = lastName + ";" + reader["lastname"];
                dob = dob + ";" + reader["DOB"];
            }
            reader.Close();
            //close the connection
            conn.Close();


            //Open the connection
            // lookup person with the requested id 
            return new Person()
            {
                UserN = userN,
                PW = pw,
                PhoneN = phoneN,
                FirstName=firstName,
                LastName=lastName,
                DOB=dob

            };
        }








        /// 
        /// Insert username, password, email and phone number to database
        ///

        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json, UriTemplate = "insertInfo/{username}/{password}/{phone}/{firstname}/{lastname}/{DOB}")]

        public Person InsertInfo(string username, string password, string phone,string firstname,string lastname,string dob)
        {
            //Declare Connection by passing the connection string from the web config file
            SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["dbString"].ConnectionString);

            //Open the connection
            conn.Open();

            //Declare the sql command
            SqlCommand cmd = new SqlCommand
                ("Insert into testTable(username,password,phone,firstname,lastname,DOB)values('" + username + "','" + password + "','" + phone +  "','" + firstname+"','" + lastname+"','" + dob+"')", conn);

            //Execute the insert query
            cmd.ExecuteNonQuery();
            cmd.Dispose();
            //close the connection
            conn.Close();


            //Open the connection
            // lookup person with the requested id 
            return new Person()
            {
                UserN = username,
                PW = password,
                PhoneN = phone,
                FirstName=firstname,
                LastName=lastname,
                DOB=dob

            };
        }




        /// 
        /// delete all information realted to a certain name
        ///

        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json, UriTemplate = "deleteInfo/{username}")]

        public string DeleteInfo(string username)
        {
            //Declare Connection by passing the connection string from the web config file
            SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["dbString"].ConnectionString);

            //Open the connection
            conn.Open();

            //Declare the sql command
            SqlCommand cmd = new SqlCommand("Delete From testTable Where username= '" + username + "'", conn);

            //Execute the insert query
            cmd.ExecuteNonQuery();
            cmd.Dispose();
            //close the connection
            conn.Close();


            return "Delete Successfully";
        }




        [WebInvoke(UriTemplate = "TestPost", Method = "POST",
                ResponseFormat = WebMessageFormat.Json,
                RequestFormat = WebMessageFormat.Json,
                BodyStyle = WebMessageBodyStyle.WrappedRequest)]


        public int Test(string value)  //Value stays null
        {
            return 0;
        }


        public string GetData(int value)
        {
            return string.Format("You entered: {0}", value);
        }

        public CompositeType GetDataUsingDataContract(CompositeType composite)
        {
            if (composite == null)
            {
                throw new ArgumentNullException("composite");
            }
            if (composite.BoolValue)
            {
                composite.StringValue += "Suffix";
            }
            return composite;
        }


        public string GetDateTime()
        {
            return DateTime.Now.ToString();
        }
    }


    public class Person
    {

        public string UserN { get; set; }

        public string PW { get; set; }

        public string PhoneN { get; set; }
        public string FirstName { get; set; } 
        public string LastName { get; set; }
        public string DOB { get; set; }
    }
}
