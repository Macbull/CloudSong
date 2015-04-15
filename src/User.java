
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Divya
 */

public class User{
String password;
int uid;
int credit;
String name;
String Gender;
String email;
int status;
Connection co;

User(){
	password=null;
	uid=0;
	credit=0;
	name=null;
	email=null;
	
}

void setPassword (String p)
{
	password=p;
}


void setUid(int r)
{
	uid=r;
}

void setCredit(int s)
{
	credit=s;
}

void setName(String t)
{
	name=t;
}

void setEmail(String u)
{
	email=u;
}

String getPassword ()
{
	return password;
}


int getUid()
{
	return uid;
}

int getCredit()
{
	return credit;
}

String getName()
{
	return name;
}

String getEmail()
{
	return email;
}

int checkEmail()
{
    
    if(!email.contains("@"))
    {
                return 1;
     }
    return 0;
}
int checkPassword()
{
    int x=0,y=0,z=0;
   for(int i=0;i<password.length();i++)
   {
                if(Character.isUpperCase(password.charAt(i)))
		x++;
                if(Character.isLowerCase(password.charAt(i)))
                y++;
                if(password.charAt(i)>=48 && password.charAt(i)<=57)
		z++;
   }
   if( x==0 || y==0 || z==0)
   return 1;
            
   return 0;
   
}
}
