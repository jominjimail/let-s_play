package database;

import java.util.Scanner; //for scanf
import java.sql.*;


public class Main {

    public static void main(String[] args) throws Exception {
        boolean flag=true;
        Scanner scan = new Scanner(System.in);
        admininformation();
        while(flag){

            int button;
            //start page select the [INT] number
            System.out.println("========= START PAGE ========");
            System.out.println("Welcome to the Music World!\n Choose the Number ! \n 1 : New Register \n 2 : UserLogin \n 3 : AdminLogin \n 4 : Exit  ");
            button = scan.nextInt(); //please int
            scan.nextLine();

            switch(button){
                case 1:
                    // Register page fill the right type value
                    System.out.println("======== REGISTER PAGE ========");
                    System.out.println("Please input your personal information");
                    System.out.println("Enter your name : ");
                    String name = scan.next();
                    System.out.println("Enter your sex ( m / w ) : ");
                    String sex = scan.next();
                    System.out.println("Enter your ssn : ");
                    int ssn = scan.nextInt();
                    System.out.println("Enter your ID : ");
                    String id = scan.next();
                    System.out.println("Enter your password: ");
                    String pass = scan.next();
                    System.out.println("Which level do you want? ( Platinum: p , Gold: g , Sliver: s , Bronze: b) : ");
                    String level = scan.next();
                    //START PAGE : Register function
                    Registerinsert(name , sex , ssn , id , pass , level);
                    break;
                case 2:
                    //user login
                    System.out.println("======== USER LOGIN ========");
                    System.out.println("Input your ID : ");
                    String loginid = scan.next();
                    System.out.println("Input you password : ");
                    String loginpswd = scan.next();

                    //call the UserLogin function
                    int check =UserLogin(loginid , loginpswd);
                    // fail the login => in UserLogin function return 0;
                    if(check==0)break;
                    else{
                        // success the login => in UserLogin function return 1; and the inter the USER PAGE
                        System.out.println("login success \n ");
                        boolean userflag= true;

                        while(userflag) {
                            // USER PAGE
                            int userbutton;
                            System.out.println("======== USER PAGE ========");
                            System.out.println(" 1 : Go to the Play List \n 2 : Music Store \n 3 : Level Edit \n 4 : Exit  ");
                            userbutton = scan.nextInt();
                            scan.nextLine();

                            switch (userbutton) {
                                case 1:
                                    //Go to the Play LIst : in here User can check their play list
                                    UserPlayList(loginid);
                                    break;
                                case 2:
                                    // Music Store : in here User can buy the new music
                                    MusicStore(loginid);
                                    break;
                                case 3:
                                    // Level Edit : in here User can change their level
                                    LevelRequest(loginid);
                                    break;
                                case 4:
                                    // Exit : exit the USER PAGE return to the START PAGE
                                    userflag=false;
                                    break;
                            }
                        }
                    }
                    break;
                case 3:
                    //admin login
                    System.out.println("======== ADMIN LOGIN ========");
                    System.out.println("Input your ID : ");
                    String adminid=scan.next();
                    System.out.println("Input you password : ");
                    String adminpswd = scan.next();
                    //call the Admin login  this function is similar to UserLogin() but, they user different DB table USER , ADMIN
                    int logincheck =AdminLogin(adminid , adminpswd);
                    // fail the login => in AdminLogin function return 0;
                    if(logincheck==0)break;
                    else {
                        // success the login => in AdminLogin function return 1; and the inter the ADMIN PAGE
                        boolean adminflag= true;

                        while (adminflag) {
                            // ADMIN PAGE
                            int adminbutton;
                            System.out.println("======== ADMIN PAGE ========");
                            System.out.println(" 1 : Music add \n 2 : Music delete \n 3 : Forced withdrawal \n 4 : Salary \n 5: Exit  ");
                            adminbutton = scan.nextInt();
                            scan.nextLine();

                            switch (adminbutton) {
                                case 1:
                                    // Admin can add the new music
                                    MusicAdd();
                                    break;
                                case 2:
                                    // Admin can delete the music
                                    MusicDelete();
                                    break;
                                case 3:
                                    // Admin can delete the User withdrawal
                                    UserDelete();
                                    break;
                                case 4:
                                    // Admin can count salary
                                    AdminSalary();
                                    break;
                                case 5:
                                    // Exit the ADMIN PAGE
                                    adminflag=false;
                                    break;
                            }
                        }
                    }
                    break;

                case 4:
                    // Exit the music world
                    flag=false;
                    break;
            }
        }
    }

    public static void admininformation(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3307/testdb?useSSL=false";
        String username = "root";
        String password = "4558";
        boolean adminflag = false;

        try {
            Class.forName(driver); //load the db
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();

            rs = stmt.executeQuery("select * From testdb.admin;");
            while(rs.next()){
                adminflag=true;
                return;
            }
            if(adminflag==false){
                stmt.executeUpdate("insert into testdb.admin (ADMID, ADMPSWD, ADMSSN) VALUE ('admin','1234',112233);");
            }

        }catch (ClassNotFoundException cnfe) {
            System.out.println("class not found");
        } catch (SQLException e) {
            System.out.println("sqlexception error\n");
            e.printStackTrace();
        }
    }

    public static int leveler(String level){
        //System.out.println("I'm here \n"+level);
        if( level.equals("p") || level.equals("P") ) return 1;
        else if(  level.equals("g") || level.equals("G")) return 2;
        else if(  level.equals("s")|| level.equals("S")) return 3;
        else if(  level.equals("b") || level.equals("B")) return 4;
        else return 0;
    }

    public static String leveler2(String level){
        //System.out.println("I'm here \n"+level);
        if( level.equals("p") || level.equals("P") ) return "P";
        else if(  level.equals("g") || level.equals("G")) return "G";
        else if(  level.equals("s")|| level.equals("S")) return "S";
        else if(  level.equals("b") || level.equals("B")) return "B";
        else return null;
    }

    //Register insert this function inserts the USER not ADMIN / ADMIN is ONLY one so , I add the admin information into db directly.
    public static void Registerinsert(String NAME, String SEX, int SSN, String ID, String PSWD, String LEVEL_p) {
        ResultSet rs = null;
        Connection conn = null;
        Statement stmt = null;
        int r;
        String levelname =null;
        levelname=leveler2(LEVEL_p);

        //First connection to my DB
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3307/testdb?useSSL=false";
        String username = "root";
        String password = "4558";

        try {
            Class.forName(driver); //load the db
            conn = DriverManager.getConnection(url, username, password);
            int ad = 112233; // new USER is managed by ADMIN
            stmt = conn.createStatement();

            //check the ID whether duplicated
            rs = stmt.executeQuery("select * From testdb.user WHERE ID='"+ID+"';");
            while(rs.next()){
                System.out.println("WARNING : There are duplicate ID, please rewrite the information");
                return;
            }
            rs.close();

            //input the information in level , user , playlist DB
            stmt.executeUpdate("insert into testdb.level "+"(LNAME, LID, LMANAGE) VALUE ('"+levelname+"',"+SSN+","+ad+");");
            r = stmt.executeUpdate("insert into testdb.user " + "(NAME, SEX, SSN, ID, PSWD, UMANAGE) value ('" + NAME + "','" + SEX + "'," + SSN + ",'" + ID + "','" + PSWD + "'," + ad + ");");
            stmt.executeUpdate("insert into testdb.playlist "+"( PNUM, POWNER) VALUE ("+0+","+SSN+");");
            if (r == 1) {
                System.out.println("Register success! \n");
            } else {
                System.out.println("Register fail \n");
            }

        }catch(ClassNotFoundException cnfe){
            System.out.println("class not found");
        }catch(SQLException e){
            System.out.println("sqlexception error\n");
            e.printStackTrace();
        }

    }

    //user login function
    public static int UserLogin(String id , String pass ) throws Exception{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String dbpasswd = null;

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3307/testdb?useSSL=false";
        String username = "root";
        String password = "4558";

        try {
            Class.forName(driver); //load the db
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
            //find password ,  ID == user's input id
            rs = stmt.executeQuery("select PSWD From testdb.user where ID='"+id+"';");

            if(rs.next()){ // if password exist load the PSWD in user table
                dbpasswd = rs.getString("PSWD");
                // if lad PSWD is equals with user's input password return 1  / NOT equal return 0
                if (dbpasswd.equals(pass)){
                    return 1;
                } else {
                    System.out.println("wrong passwd please login again \n");
                    return 0;
                }
            }else { // if user's input id is not exist return 0
                System.out.println("wrong id please login again \n");
                return 0;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    return 0;
    }

    //Admin login function
    public static int AdminLogin(String id , String pass ) throws Exception{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String dbpasswd = null;

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3307/testdb?useSSL=false";
        String username = "root";
        String password = "4558";

        try {
            Class.forName(driver); //load the db
            conn = DriverManager.getConnection(url, username, password);

            stmt = conn.createStatement();
            rs = stmt.executeQuery("select ADMPSWD From testdb.admin where ADMID='"+id+"';");

            if(rs.next()) {

                dbpasswd = rs.getString("ADMPSWD");
                //System.out.println("is it right?" + dbpasswd);

                if (dbpasswd.equals(pass)){
                    System.out.println("login success \n ");
                    return 1;
                }
                else {
                    System.out.println("wrong passwd please login again \n");
                    return 0;
                }
            }else {
                System.out.println("wrong id please login again \n");
                return 0;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    //admin can music add
    public static void MusicAdd(){
        Scanner scan = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3307/testdb?useSSL=false";
        String username = "root";
        String password = "4558";

        try {
            Class.forName(driver); //load the db
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
            int ad = 112233;
            String ans;
            //limit the music genre number 3
            String[] arr = new String[3];
            arr[0]=null;arr[1]=null;arr[2]=null;

            System.out.println("Do you want to check music list ? ( y / n )");
            ans = scan.next();
            //check the MUSIC LIST before the ADD
            if(ans.equals("y")){
                System.out.println("========  MUSIC LIST ========");
                System.out.println("ID  M-NAME   M-SINGER ");
                // execute the sql : to see the music information in music table
                rs = stmt.executeQuery("select * From testdb.music;");
                while(rs.next()){
                    int mid = rs.getInt(1);
                    String mname = rs.getString(2);
                    String msinger = rs.getString(3);
                    System.out.println(mid+" \t"+mname+"\t "+msinger);
                }
            }
            // MUSIC ADD PAGE
            System.out.println("======== MUSIC ADD ========");
            System.out.println("Input the music information");
            System.out.println("M ID : ");
            int mid = scan.nextInt();
            System.out.println(("M NAME : "));
            String mname = scan.next();
            System.out.println("M SINGER : ");
            String msinger = scan.next();
            System.out.println("M GENRE num (max genre num is 3): ");
            int genrenum = scan.nextInt();
            for(int i=0 ;i<genrenum;i++){
                arr[i]=scan.next();
            }
            System.out.println("M Lyric : ");
            String mlyric = scan.next();
            // execute the sql : to add the music in music table
            stmt.executeUpdate("insert into testdb.music "+"(MID, MNAME, MSINGER, MLIKE, MMANAGE,MLYRIC) VALUE ("+mid+",'"+mname+"','"+msinger+"',"+0+","+ad+",'"+mlyric+"');");
            System.out.println("Music add success!");
            // execute the sql : to add the music genre in mgenre table
            for(int i=0;i<genrenum;i++) {
                if(arr[i] != null) {
                    stmt.executeUpdate("insert into testdb.mgenre " + "(MMID, MGENRE) VALUE (" + mid + ",'" + arr[i]+"');");
                }
            }
        }catch(ClassNotFoundException cnfe){
            System.out.println("class not found");
        }catch(SQLException e) {
            System.out.println("sqlexception error\n");
            e.printStackTrace();
        }
    }

    //admin can music delete
    public static void MusicDelete() {
        Scanner scan = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int[] arrpid = new int[50]; // for save the delete music mid --> find user PID
        int[] arrpnum = new int[50]; // for save the delete music mid's [PNUM]
        for(int i=0;i<50;i++){
            arrpid[i]=0;
            arrpnum[i]=0;
        }
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3307/testdb?useSSL=false";
        String username = "root";
        String password = "4558";

        try {
            Class.forName(driver); //load the db
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
            String ans;

            System.out.println("Do you want to check music list ? ( y / n )");
            ans = scan.next();
            if (ans.equals("y")) {
                System.out.println("======== MUSIC LIST ========");
                System.out.println("ID  M-NAME   M-SINGER ");
                // execute the sql : for check the music list
                rs = stmt.executeQuery("select * From testdb.music;");
                while (rs.next()) {
                    int mid = rs.getInt(1);
                    String mname = rs.getString(2);
                    String msinger = rs.getString(3);
                    System.out.println(mid + "\t" + mname + "\t " + msinger);
                }
            }
            System.out.println("========= MUSIC DELETE ========");
            System.out.println("Input the music MID to delete ");
            int deletemid = scan.nextInt();
            int i=0;

            //find user[PID] who have deletemid [MID] and then subtract the user's [PNUM] -1
            rs = stmt.executeQuery("select * From testdb.musiccontain,testdb.playlist WHERE CMID="+deletemid+" AND PID=CPID;");
            while (rs.next()) {
                int findpid = rs.getInt("PID");
                int findpnum = rs.getInt("PNUM");
                arrpid[i]=findpid;
                arrpnum[i]=findpnum-1;
                i=i+1;
            }
            // execute the sql : first : update the [PNUM]
            for(int j=0;j<i;j++){
                stmt.executeUpdate("UPDATE testdb.playlist " + "SET PNUM=" + arrpnum[j] + " WHERE PID=" + arrpid[j] + ";");
            }
            rs.close();

            // execute the sql : second : delete the music in music db
            stmt.executeUpdate("delete from testdb.music " + "where MID=" + deletemid + ";");
            System.out.println("Music delete success!");

        } catch (ClassNotFoundException cnfe) {
            System.out.println("class not found");
        } catch (SQLException e) {
            System.out.println("sqlexception error\n");
            e.printStackTrace();
        }
    }

    //admin cna User delete
    public static void UserDelete(){
        Scanner scan = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3307/testdb?useSSL=false";
        String username = "root";
        String password = "4558";

        try {
            Class.forName(driver); //load the db
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
            String ans;

            System.out.println("Do you want to check user list ? ( y / n )");
            ans = scan.next();
            if (ans.equals("y")) {
                System.out.println("======== USER LIST ========");
                System.out.println("NAME   SSN   ID   PSWD ");
                // execute the sql : check the user table
                rs = stmt.executeQuery("select * From testdb.user;");
                while (rs.next()) {
                    String name = rs.getString(1);
                    int ssn = rs.getInt(3);
                    String id = rs.getString(4);
                    String pswd = rs.getString(5);

                    System.out.println(name + "\t" + ssn + "\t " + id + "\t"+pswd);
                }
                rs.close();
            }
            //USER DELETE : get the ssn input
            System.out.println("========= USER DELETE ========");
            System.out.println("Input the user SSN to delete ");
            int dssn = scan.nextInt();

            // execute the sql : check :  user' input ssn (dssn) exist in USER table ?
            boolean existflag = false;
            rs = stmt.executeQuery("select * From testdb.user WHERE SSN="+dssn+";");
            while (rs.next()) {
                existflag=true;
            }
            rs.close();

            if(existflag==false){
                // Not exist
                System.out.println("WRONG : You input NOT EXIST [SSN] ");
            }else if(existflag==true) {
                // right input exist
                //execute the sql : delete the level , user table if user'input ssn is correct the LID , SSN
                stmt.executeUpdate("delete from testdb.level " + "where LID=" + dssn + ";");
                stmt.executeUpdate("delete from testdb.user " + "where SSN=" + dssn + ";");
                System.out.println("Delete Success");
            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println("class not found");
        } catch (SQLException e) {
            System.out.println("sqlexception error\n");
            e.printStackTrace();
        }
    }

    //Admin Salary
    public static void AdminSalary(){
        int sum=0;
        int usernum=0;
        int[] levelbox = new int[5];
        levelbox[4]=0;levelbox[1]=0;levelbox[2]=0;levelbox[3]=0;
        Scanner scan = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3307/testdb?useSSL=false";
        String username = "root";
        String password = "4558";

        try {
            Class.forName(driver); //load the db
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
            System.out.println("======== ADMIN SALARY ========");
            //execute the sql : load the all user's level
            rs = stmt.executeQuery("select * From testdb.level;");
            while (rs.next()) {
                String levelname = rs.getString(1);
                int pay = (6-leveler(levelname))*1000;
                int levelnum = leveler(levelname);
                levelbox[levelnum]=levelbox[levelnum]+1;
                sum = sum +pay;
                usernum= usernum+1;
            }
            System.out.println("Admin salary is "+sum);
            System.out.println("Music World User number is "+usernum);
            System.out.println("Detail information \n Platinum : "+levelbox[1]+"\n Gold : "+levelbox[2]+"\n Silver : "+levelbox[3]+"\n Bronze : "+levelbox[4]);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("class not found");
        } catch (SQLException e) {
            System.out.println("sqlexception error\n");
            e.printStackTrace();
        }



    }


    //User Play List
    public static void UserPlayList(String loginid) {
        //print the User's music list
        System.out.println("======== MUSIC LIST ========");
        int[] arr = new int[50]; // for save the music mid in musiccontain db
        for(int j=0;j<50;j++){
            arr[j]=0;
        }// init the musiccontain save box
        int musicnum = 0; // for count the music num at list

        Scanner scan = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int updateplaylistnum=0;

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3307/testdb?useSSL=false";
        String username = "root";
        String password = "4558";

        try {
            Class.forName(driver); //load the db
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();

            int playlistid=0;
            String mlyric=null;
            // execute the sql : get the pid by use loginid
            rs = stmt.executeQuery("select PID From testdb.user,testdb.playlist WHERE ID='" + loginid + "' AND SSN = POWNER;");
            while(rs.next()) {
                playlistid = rs.getInt("PID");
            }
            rs.close();
            // execute the sql : print the user's play list and save the information in arr
            rs = stmt.executeQuery("select MID,MNAME,MSINGER FROM testdb.musiccontain,testdb.music WHERE CMID=MID AND CPID="+playlistid+";");
            while(rs.next()){
                int mid = rs.getInt(1);
                arr[musicnum]=mid;
                String mname = rs.getString(2);
                String msinger = rs.getString(3);
                System.out.println(mid + "\t" + mname + " \t" + msinger);
                musicnum=musicnum+1;
            }
            rs.close();

            //play the music / delete the music / exit the USER MUSIC LIST
            boolean playflag = true;
            int playbutton;

            while (playflag) {
                System.out.println("===========================\n 1 : Music Play \n 2 : Music Delete \n 3 : Exit");
                playbutton= scan.nextInt();
                scan.nextLine();

                switch(playbutton) {
                    case 1:
                        // music play --> show the music genre,lyric
                        System.out.println("Choose the music id you want to play : ");
                        int playmid = scan.nextInt();
                        boolean rightinput = false;
                        // check : really exist  input mid num in user play list ?
                        for (int i = 0; i < musicnum; i++) {
                            // already we save the music list at arr
                            if (playmid == arr[i]) {
                                // exist in user's list
                                rightinput = true;
                                // first print the music genre
                                System.out.print("MUSIC GENRE : ");
                                rs = stmt.executeQuery("select MGENRE From testdb.mgenre WHERE MMID='" + playmid + "';");
                                while(rs.next()) {
                                    String mgenre = rs.getString("MGENRE");
                                    System.out.print(mgenre+" ");
                                }
                                rs.close();

                                // second print the music lyrics
                                rs = stmt.executeQuery("select MLYRIC FROM testdb.music WHERE MID=" + playmid + ";");
                                while (rs.next()) {
                                    String lyric = rs.getString("MLYRIC");
                                    System.out.println("\n"+lyric);
                                }
                                rs.close();
                            }
                        }//for end
                        if (rightinput == false) {
                            // user input wrong music num
                            System.out.println("WRONG : You input the wrong music id, please input again ");
                        }
                        break;
                    case 2:
                        //User can music delete in their play list $change the PLAYLIST 's COLUMN [PNUM]
                        System.out.println("Choose the music id you want to delete : ");
                        int deletemid = scan.nextInt();
                        boolean rightinput2 = false;
                        // check : really exist  delete mid num in user play list ?
                        for (int i = 0; i < musicnum; i++) {
                            // already we save the user's play list music mid
                            if (deletemid == arr[i]) {
                                // exist
                                rightinput2 = true;
                                //subtract -1 [PNUM] and change null the arr[] value to null
                                musicnum=musicnum-1;
                                arr[i]=0;
                                // execute the sql : delete the music in user's musiccontain db
                                stmt.executeUpdate("delete from testdb.musiccontain " + "where CMID=" + deletemid + " AND CPID="+playlistid+";");
                                // execute the sql : get the play list [PNUM] num in level table
                                rs = stmt.executeQuery("select PNUM From testdb.playlist WHERE PID=" + playlistid + ";");
                                while (rs.next()) {
                                    updateplaylistnum = rs.getInt("PNUM");
                                    updateplaylistnum = updateplaylistnum - 1;
                                }
                                // execute the sql : update the music list number
                                stmt.executeUpdate("UPDATE testdb.playlist " + "SET PNUM=" + updateplaylistnum + " WHERE PID=" + playlistid + ";");
                                rs.close();
                            }
                        }
                        if (rightinput2 == false) {
                            // user input the wrong mid
                            System.out.println("WRONG : You input the wrong music id, please input again ");
                        }
                        break;
                    case 3:
                        //user music list exit --> go back USER PAGE
                        playflag=false;
                        break;
                }
            }//while(playflag)
        } catch (ClassNotFoundException cnfe) {
            System.out.println("class not found");
        } catch (SQLException e) {
            System.out.println("sqlexception error\n");
            e.printStackTrace();
        }
    }

    //Music Store
    public static void MusicStore(String loginid) {
        System.out.println("========= MUSIC STORE ========");
        Scanner scan = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3307/testdb?useSSL=false";
        String username = "root";
        String password = "4558";

        try {
            Class.forName(driver); //load the db
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
            //print the music list
            System.out.println("ID  M-NAME   M-SINGER ");
            rs = stmt.executeQuery("select * From testdb.music;");
            while (rs.next()) {
                int mid = rs.getInt(1);
                String mname = rs.getString(2);
                String msinger = rs.getString(3);
                System.out.println(mid + "\t" + mname + "\t" + msinger);
            }
            rs.close();

            boolean storeflag = true;
            System.out.println("Do you want to add the music? (y / n) ");
            String ans = scan.next();

            if (ans.equals("n")) {
                //storeflag = false;
                return;
            } else {
                // add the music in user's play list
                int playlistid=0;
                int playlistnum=0;
                int musiclikenum=0;
                int updateplaylistnum=0;
                int ssn=0;
                String levelname=null;
                int checkmid=0;

                //get the [PID] , [SSN] by use loginid
                rs = stmt.executeQuery("select PID,SSN From testdb.user,testdb.playlist WHERE ID='" + loginid + "' AND SSN = POWNER;");
                while(rs.next()) {
                    playlistid = rs.getInt("PID");
                    ssn=rs.getInt("SSN");
                }
                rs.close();

                while (storeflag) {
                    //check [PNUM] whether user can add the music compare with LEVEL table [LNAME]
                    rs = stmt.executeQuery("select PNUM From testdb.playlist WHERE PID=" + playlistid + ";");
                    while (rs.next()) {
                        //playlistnum : the number of music in User's play list
                        playlistnum = rs.getInt("PNUM");
                    }
                    rs.close();
                    rs = stmt.executeQuery("select LNAME From testdb.level,testdb.playlist WHERE LID=POWNER AND POWNER="+ssn+";");
                    while (rs.next()) {
                        levelname = rs.getString("LNAME");
                    }
                    rs.close();
                    // maxmusicnum :  the MAX Number of music according to level
                    int maxmusicnum = (5 - leveler(levelname)) * 10;
                    if (playlistnum == maxmusicnum) {
                        // User can't add the music in their play list because over the maxmusicnum
                        System.out.println("WARNING : Your play list is full, please update the level first ");
                        return;
                    } else {
                        //add the music in the user's play list
                        System.out.println("Input the MID number to add play list :");
                        int mid = scan.nextInt();
                        boolean dupflag=false;
                        //duplicate music add is not allow
                        rs = stmt.executeQuery("select CMID From testdb.musiccontain WHERE CPID="+playlistid+" AND CMID="+mid+";");
                        while (rs.next()){
                            checkmid = rs.getInt("CMID");
                            if(checkmid==mid){
                                dupflag=true;
                                System.out.println("WARNING : There are duplicate music in your play list, ");
                                break;
                            }
                        }
                        rs.close();
                        //check mid  : user input Music num exist in MUSIC db?
                        rs = stmt.executeQuery("select MID From testdb.music WHERE MID="+mid+";");
                        while (rs.next()){
                            //if exist ---> come here! and change the checkmid != 0
                            checkmid = rs.getInt("MID");
                                break;
                        }
                        if(checkmid == 0){
                            // not exist ---> finish !
                            dupflag=true;
                            System.out.println("WARNING : There not exist music in MUSIC STORE,");
                        }

                        if(dupflag==true){
                            System.out.println(" please rewrite the mid");
                        }else if(dupflag==false){
                            //add the information in musiccontain table
                            stmt.executeUpdate("insert into testdb.musiccontain " + "(CPID , CMID) VALUE (" + playlistid + "," + mid + ");");
                            //for add the music like number get the information
                            rs = stmt.executeQuery("select MLIKE From testdb.music WHERE MID=" + mid + ";");
                            while (rs.next()) {
                                musiclikenum = rs.getInt("MLIKE");
                                musiclikenum = musiclikenum + 1;
                            }
                            // update the music like number
                            stmt.executeUpdate("UPDATE testdb.music " + "SET MLIKE=" + musiclikenum + " WHERE MID=" + mid + ";");
                            rs.close();

                            //update the play list number +1 when user add the music
                            rs = stmt.executeQuery("select PNUM From testdb.playlist WHERE PID=" + playlistid + ";");
                            while (rs.next()) {
                                updateplaylistnum = rs.getInt("PNUM");
                                updateplaylistnum = updateplaylistnum + 1;
                            }
                            // update the music list num
                            stmt.executeUpdate("UPDATE testdb.playlist " + "SET PNUM=" + updateplaylistnum + " WHERE PID=" + playlistid + ";");
                            rs.close();

                            System.out.println("Do you want to add more music ? ( y / n )");
                            String moreans = scan.next();
                            if (moreans.equals("n")) {
                                storeflag = false;
                            }
                        }
                    }
                }
            }
            }catch(ClassNotFoundException cnfe){
                System.out.println("class not found");
            }catch(SQLException e){
                System.out.println("sqlexception error\n");
                e.printStackTrace();
            }
        }

    //LevelRequest
    public static void LevelRequest(String loginid){

        Scanner scan = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String userlevelname=null;
        int currentmusicnum=0;
        boolean levelflag = true;
        int levelbutton;
        int ssn=0;

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3307/testdb?useSSL=false";
        String username = "root";
        String password = "4558";

        try {
            Class.forName(driver); //load the db
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();

            while (levelflag) {
                System.out.println("======== LEVEL REQUEST ========");
                //get the user's Level name [LNAME] by use loginid
                rs = stmt.executeQuery("select * From testdb.user,testdb.level  WHERE ID='" + loginid + "' AND SSN = LID;");
                while(rs.next()) {
                    userlevelname = rs.getString("LNAME");
                    ssn=rs.getInt("SSN");
                }
                rs.close();
                // get the user's current music num [PNUM] in playlist db
                rs = stmt.executeQuery("select PNUM From testdb.playlist  WHERE POWNER="+ssn+";");
                while(rs.next()) {
                    currentmusicnum = rs.getInt("PNUM");
                }
                rs.close();

                System.out.println("Your current level is " + userlevelname);
                int userlevelnameNum = leveler(userlevelname);
                String editans = null;
                String editlevel= null;
                System.out.println(" 1 : Upgrade \n 2 : Downgrade \n 3 : Exit");

                levelbutton= scan.nextInt();
                scan.nextLine();

                switch(levelbutton) {
                    case 1: // upgrade

                        switch (userlevelnameNum){
                            case 1: //Platinum user can't upgrade the level
                                System.out.println("SORRY : Your current level is the best ");
                                break;
                            case 2: //Gold user can upgrade to Platinum
                                System.out.println("You can Upgrade to Platinum the cost is 5000 for month. \n Do you want to upgrade your level? ( y / n )");
                                editans=scan.next();
                                if(editans.equals("y")){
                                    editlevel="P";
                                    LevelEditMachine(editlevel,ssn);
                                }else if(editans.equals("n")){
                                    System.out.println("You canceled the request Bye.");
                                }else {
                                    System.out.println("WRONG : Your input is incorrect. ");
                                }
                                break;
                            case 3: //Sliver user can upgrade to Gold or Platinum
                                System.out.println("You can Upgrade to Gold or Platinum \nPlatinum : the cost is 5000 for month.\nGold : the cost is 4000 for month.\n Do you want to upgrade your level? ( y / n )");
                                editans=scan.next();
                                if(editans.equals("y")){
                                    System.out.println("Input the level you want to upgrade");
                                    editlevel=scan.next();
                                    editlevel=leveler2(editlevel);
                                    LevelEditMachine(editlevel,ssn);
                                }else if(editans.equals("n")){
                                    System.out.println("You canceled the request Bye.");
                                }else {
                                    System.out.println("WRONG : Your input is incorrect. ");
                                }
                                break;
                            case 4: // Bronze user can upgrade to S , G , P
                                System.out.println("You can Upgrade to Gold or Platinum or Silver \nPlatinum : the cost is 5000 for month.\nGold : the cost is 4000 for month.  \nSilver : the cost is 3000 for month.\n Do you want to upgrade your level? ( y / n )");
                                editans=scan.next();
                                if(editans.equals("y")){
                                    System.out.println("Input the level you want");
                                    editlevel=scan.next();
                                    editlevel=leveler2(editlevel);
                                    LevelEditMachine(editlevel,ssn);
                                }else if(editans.equals("n")){
                                    System.out.println("You canceled the request Bye.");
                                }else {
                                    System.out.println("WRONG : Your input is incorrect. ");
                                }
                                break;
                        }
                        break;
                    case 2: // down grade
                        switch (userlevelnameNum){
                            case 1: // platinum B S G
                                System.out.println("You can Downgrade to Bronze or Silver of Gold\nGold : the cost is 4000 for month.\nSliver : the cost is 3000 for month.\nBronze : the cost is 2000 for month.  \n Do you want to upgrade your level? ( y / n )");
                                editans=scan.next();
                                if(editans.equals("y")){
                                    System.out.println("Input the level you want to downgrade");
                                    editlevel=scan.next();
                                    editlevel=leveler2(editlevel);
                                    if(DowngradeRight(currentmusicnum,editlevel)==1){
                                        LevelEditMachine(editlevel,ssn);
                                    }else{
                                        System.out.println("WARNING : You can't downgrade level , please delete the music first");
                                    }
                                }else if(editans.equals("n")){
                                    System.out.println("You canceled the request Bye.");
                                }else {
                                    System.out.println("WRONG : Your input is incorrect. ");
                                }
                                break;
                            case 2: // gold B S
                                System.out.println("You can Downgrade to Bronze or Silver \nSliver : the cost is 3000 for month.\nBronze : the cost is 2000 for month.  \n Do you want to upgrade your level? ( y / n )");
                                editans=scan.next();
                                if(editans.equals("y")){
                                    System.out.println("Input the level you want to downgrade");
                                    editlevel=scan.next();
                                    editlevel=leveler2(editlevel);
                                    if(DowngradeRight(currentmusicnum,editlevel)==1){
                                        LevelEditMachine(editlevel,ssn);
                                    }else{
                                        System.out.println("WARNING : You can't downgrade level, please delete the music first");
                                    }
                                }else if(editans.equals("n")){
                                    System.out.println("You canceled the request Bye.");
                                }else {
                                    System.out.println("WRONG : Your input is incorrect. ");
                                }
                                break;
                            case 3: // silver --> B
                                System.out.println("You can Downgrade to Bronze the cost is 2000 for month. \n Do you want to upgrade your level? ( y / n )");
                                editans=scan.next();
                                if(editans.equals("y")){
                                    editlevel="B";
                                    if(DowngradeRight(currentmusicnum,editlevel)==1){
                                        LevelEditMachine(editlevel,ssn);
                                    }else{
                                        System.out.println("WARNING : You can't downgrade level , please delete the music first");
                                    }
                                }else if(editans.equals("n")){
                                    System.out.println("You canceled the request Bye.");
                                }else {
                                    System.out.println("WRONG : Your input is incorrect. ");
                                }
                                break;
                            case 4: // bronze --> no
                                System.out.println("SORRY : Your current level is the lowest ");
                                break;
                        }
                        break;
                    case 3: // exit
                        levelflag=false;
                        break;
                }
            }
        }catch (ClassNotFoundException cnfe) {
            System.out.println("class not found");
        } catch (SQLException e) {
            System.out.println("sqlexception error\n");
            e.printStackTrace();
        }
    }

    //Level Edit machine
    public static void LevelEditMachine(String level ,int ssn){
        Connection conn = null;
        Statement stmt = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3307/testdb?useSSL=false";
        String username = "root";
        String password = "4558";

        try {
            Class.forName(driver); //load the db
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();

            // update the level table
            stmt.executeUpdate("UPDATE testdb.level " + "SET LNAME='" + level + "' WHERE LID=" + ssn + ";");

        }catch (ClassNotFoundException cnfe) {
            System.out.println("class not found");
        } catch (SQLException e) {
            System.out.println("sqlexception error\n");
            e.printStackTrace();
        }
    }

    // if Down level input is right use this function
    public static int DowngradeRight(int currentmusicnum , String level){
        int limitnum = (5-leveler(level))*10;

        if(currentmusicnum > limitnum){
            return 0;
        }
        return 1;
    }


}
