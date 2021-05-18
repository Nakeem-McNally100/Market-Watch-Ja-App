import mysql.connector
import random
from datetime import date, datetime, timedelta



#database information

hostName = "localhost"
databaseUser = "root"
databasePassword = ""
databaseName = "marketwatchja"

def connect():
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection sucessful")
    else:
        print("connection unsuccesful")

    mycursor = mydb.cursor()
    #mycursor.execute("CREATE DATABASE LatchedJamaica")
    #mycursor.execute("Show databases")
    #for db in mycursor:
        #print(db)
    return  mydb, mycursor
#connect() 
      

def insertUser(UserID, Email, Password, FullName):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    addUser = ("INSERT INTO UserProfile(UserID, Email, Password, FullName) VALUES (%s, %s, %s, %s)")
    Userdata = (UserID, Email, Password, FullName)
    mycursor.execute(addUser, Userdata)
    mydb.commit()
    mycursor.close()
    mydb.close()


def insertCompanies():
    myfile = open('company name and ticket.txt', 'r')
    data = myfile.readlines()
    i = 0
    for x in data:
        i += 1
        company = x.split('+')
        company[1] = company[1].split(',')[0]
        insertCompany(company[1],company[0])


    

def insertCompany(CompanyName, Ticker):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    addCompany = ("INSERT INTO companyprofile(CompanyName, Ticker) VALUES (%s, %s)")
    Companydata = (CompanyName, Ticker)
    mycursor.execute(addCompany, Companydata)
    mydb.commit()
    mycursor.close()
    mydb.close()


def insertSentimentResults(CompanyName,positiveValue, negativeValue,neutralValue,subjectivityValue,objectivityValue,totalTweets):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    c = str(random.randint(1000,2000))
    SentimentID = "sent" + c
    SentimentDate = datetime.today()
    addSentiment = ("INSERT INTO SentimentResults(CompanyName,SentimentID,positiveValue, negativeValue,neutralValue,subjectivityValue,objectivityValue,totalTweets,SentimentDate) VALUES (%s, %s,%s,%s, %s, %s, %s, %s, %s)")
    Sentimentdata = (CompanyName,SentimentID,positiveValue, negativeValue,neutralValue,subjectivityValue,objectivityValue,totalTweets,SentimentDate)
    mycursor.execute(addSentiment, Sentimentdata)
    mydb.commit()
    mycursor.close()
    mydb.close()

                            
def insertHealtResults(CompanyName,free_cashflow,operating_cashflow,
                       currentratio,current_assets1
                      ,current_liab1,noncurrent_assets1,noncurrent_liab1,
                      acidtest,assetsturnover,grossmargin,debtratio,debtcoverage
                      ,working_capital1):   
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    HealthID = 'health' + str(random.randint(1000,2000))
    HealthDate = datetime.today()
    addHealth = ("""INSERT INTO healthresults(CompanyName, HealthID, free_cashflow,operating_cashflow,currentratio,current_assets1
                      ,current_liab1,noncurrent_assets1,noncurrent_liab1,
                      acidtest,assetsturnover,grossmargin,debtratio,debtcoverage
                      ,working_capital1, reviewHealthDate) VALUES (%s, %s, %s, %s, %s, %s, %s, %s,%s, %s, %s, %s, %s, %s, %s, %s)""")

    Healthdata = (CompanyName,HealthID,free_cashflow,operating_cashflow,
                       currentratio,current_assets1
                      ,current_liab1,noncurrent_assets1,noncurrent_liab1,
                      acidtest,assetsturnover,grossmargin,debtratio,debtcoverage
                      ,working_capital1, HealthDate)


    mycursor.execute(addHealth, Healthdata)
    mydb.commit()
    mycursor.close()
    mydb.close()


def insertPridictionResults(CompanyID, PredictionID, PredictionResultsData, PredictionDate):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    addPridiction = ("INSERT INTO PridictionResults(CompanyID, PredictionID, PredictionResultsData, PredictionDate) VALUES (%s, %s, %s, %s)")
    Predictiondata = (CompanyID, PredictionID, PredictionResultsData, PredictionDate)
    mycursor.execute(addPridiction, Predictiondata)
    mydb.commit()
    mycursor.close()
    mydb.close()


    
def getUserProfile(email):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    query = ("SELECT * FROM UserProfile WHERE Email = %s")
    mycursor.execute(query, (email,))
    result = mycursor.fetchall()
    mydb.commit()
    mycursor.close()
    mydb.close()
    return {'userid': result[0][0],'email': result[0][1],'password': result[0][2],'fullname': result[0][3]}

def getSentimentResults(CompanyName):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()

    #SELECT * FROM foo WHERE foo.Dates = (SELECT MAX(Dates) FROM foo) AND SentimentResults.SentimentDate = (SELECT MAX(SentimentDate) FROM SentimentResults)
    
    query = ("SELECT * FROM SentimentResults WHERE CompanyName = %s ORDER BY SentimentDate")
    mycursor.execute(query, (CompanyName,))
    myresult = mycursor.fetchall()
    mydb.commit()
    mycursor.close()
    mydb.close()
    return myresult[-1]

def getCompanies():
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    query = ("SELECT * FROM CompanyProfile")
    mycursor.execute(query)
    myresult = mycursor.fetchall()
    mydb.commit()
    mycursor.close()
    mydb.close()
    return myresult

def getTicker(company):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    query = ("SELECT Ticker FROM CompanyProfile WHERE CompanyName = %s")
    mycursor.execute(query, (company,))
    myresult = mycursor.fetchall()
    mydb.commit()
    mycursor.close()
    mydb.close()
    return myresult[0][0]

    


def getHealthResults(CompanyName):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    query = ("SELECT * FROM healthresults WHERE CompanyName = %s ORDER BY reviewHealthDate")
    mycursor.execute(query, (CompanyName,))
    myresult = mycursor.fetchall()
    mydb.commit()
    mycursor.close()
    mydb.close()
    return myresult[-1]

def getPredictionResults(CompanyID):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    query = ("SELECT * FROM PredictionResults WHERE CompanyID = %s")
    mycursor.execute(query, (CompanyID,))
    myresult = mycursor.fetchall()
    mydb.commit()
    mycursor.close()
    mydb.close()
    return myresult

def getSearchHistory(UserID):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    query = ("SELECT * FROM SearchHistory WHERE UserID = %s")
    mycursor.execute(query, (UserID,))
    myresult = mycursor.fetchall()
    mydb.commit()
    mycursor.close()
    mydb.close()
    return myresult

def getNewsArticle(UserID):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    query = ("SELECT * FROM SearchHistorye WHERE UserID = %s")
    mycursor.execute(query, (UserID,))
    myresult = mycursor.fetchall()
    mydb.commit()
    mycursor.close()
    mydb.close()
    return myresult

def deletefrom(table, ID):
    tabledic={'UserProfile':'UserID','NewsArticle':'UserID','SearchHistory':'UserID','CompanyProfile':'CompanyID','SentimentResults':'CompanyID','HealtResults':'CompanyID','PredictionResults':'CompanyID'}
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    query = ("DELETE FROM %s WHERE %s = %s")
    querydata=(table,tabledic[table],ID)
    mycursor.execute(query, querydata)
    mydb.commit()
    mycursor.close()
    mydb.close()

def buildTables():
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    tables = {}
    #tables['UserProfile'] = ("create table UserProfile(UserID varchar(10),Email varchar(50),Password varchar(30),FullName varchar(50),primary key(UserID))")
    #tables['CompanyProfile']=("create table CompanyProfile(CompanyName varchar(30),Ticker varchar(20),primary key(CompanyName))")
    tables['SentimentResults'] = ("create table SentimentResults(CompanyName varchar(30),SentimentID varchar(10),positiveValue int, negativeValue int,neutralValue int,subjectivityValue int,objectivityValue int,totalTweets int,SentimentDate DATE,primary key(SentimentID),foreign key(CompanyName) references CompanyProfile(CompanyName) on delete cascade);")

    #tables['HealtResults'] = ("""create table HealthResults(CompanyName varchar(30),
    #                       HealthID varchar(10),
    #                        free_cashflow FLOAT(50),operating_cashflow FLOAT(50),
    #                       currentratio FLOAT(50),current_assets1 FLOAT(50)
    #                  ,current_liab1 FLOAT(50),noncurrent_assets1 FLOAT(50),noncurrent_liab1 FLOAT(50),
    #                  acidtest FLOAT(50),assetsturnover FLOAT(50),grossmargin FLOAT(50),
    #                  debtratio FLOAT(50),debtcoverage FLOAT(50),working_capital1 FLOAT(50),
    #                reviewHealthDate DATE, primary key(HealthID),
    #            foreign key(CompanyName) references CompanyProfile(CompanyName) on delete cascade);""")

    #tables['PredictionResults'] = ("create table PredictionResults(CompanyID varchar(10),PredictionID varchar(10),PredictionResultsData varchar(100), #requires reviewPredictionDate DATE,primary key(PredictionID)foreign key(CompanyID) references UserProfile(CompanyProfile) on delete cascade);")
    #tables['SearchHistory'] = ("create table SearchHistory(UserID varchar(10),SearchID varchar(10),SearchKeyword varchar(50), SearchDate DATE,primary key(SearchID)foreign key(UserID) references UserProfile(UserID) on delete cascade);")
    #tables['NewsArticle'] = ("create table NewsArticle(ArticleID varchar(10),Title varchar(100),ThumbnailLink varchar(100),ArticleLink varchar(100),Description varchar(100), ArticleDate DATE,primary key(ArticleID)  );")

    for table_name in tables:
        table_description = tables[table_name]
        try:
            print("Creating table {}: ".format(table_name), end='')
            mycursor.execute(table_description)
        except mysql.connector.Error as err:
            if err.errno == errorcode.ER_TABLE_EXISTS_ERROR:
                print("already exists.")
            else:
                print(err.msg)
        else:
            print("OK")

    mycursor.close()
    mydb.close()



