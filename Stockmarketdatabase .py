import mysql.connector
from datetime import date, datetime, timedelta



#database information

hostName = "localhost"
databaseUser = "root"
databasePassword = ""
databaseName = "latchjadb"


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

def insertCompany(CompanyID, CompanyName):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    addCompany = ("INSERT INTO CompanyProfile(CompanyID, CompanyName) VALUES (%s, %s)")
    Companydata = (CompanyID, CompanyName)
    mycursor.execute(addCompany, Companydata)
    mydb.commit()
    mycursor.close()
    mydb.close()

def insertSentimentResults(CompanyID, SentimentID, SentimentResultsData, SentimentDate):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    addSentiment = ("INSERT INTO SentimentResults(CompanyID, SentimentID, SentimentResultsData, SentimentDate) VALUES (%s, %s, %s, %s)")
    Sentimentdata = (CompanyID, SentimentID, SentimentResultsData, SentimentDate)
    mycursor.execute(addSentiment, Sentimentdata)
    mydb.commit()
    mycursor.close()
    mydb.close()

def insertHealtResults(CompanyID, HealthID, HealthResultsData, HealthDate):   
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    addHealth = ("INSERT INTO HealtResults(CompanyID, HealthID, HealthResultsData, HealthDate) VALUES (%s, %s, %s, %s)")
    Healthdata = (CompanyID, HealthID, HealthResultsData, HealthDate)
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

def insertSearchHistory(UserID, SearchID, SearchKeyword, SearchDate):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    addSearch = ("INSERT INTO SearchHistory(UserID, SearchID, SearchKeyword, SearchDate) VALUES (%s, %s, %s, %s)")
    Searchdata = (UserID, SearchID, SearchKeyword, SearchDate)
    mycursor.execute(addSearch, Searchdata)
    mydb.commit()
    mycursor.close()
    mydb.close()

def insertNewsArticle(ArticleID, Title, ThumbnailLink, ArticleLink, Description, ArticleDate):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    addNewsArticle = ("INSERT INTO NewsArticle(ArticleID, Title, ThumbnailLink, ArticleLink, Description, ArticleDate) VALUES (%s, %s, %s, %s, %s, %s)")
    NewsArticledata = (ArticleID, Title, ThumbnailLink, ArticleLink, Description, ArticleDate)
    mycursor.execute(addNewsArticle, NewsArticledata)
    mydb.commit()
    mycursor.close()
    mydb.close()
    
def getUserProfile(UserID):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    query = ("SELECT * FROM UserProfile WHERE UserID = %s")
    mycursor.execute(query, (UserID,))
    myresult = mycursor.fetchall()
    mydb.commit()
    mycursor.close()
    mydb.close()
    return myresult

def getSentimentResults(CompanyID):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    query = ("SELECT * FROM SentimentResults WHERE CompanyID = %s")
    mycursor.execute(query, (CompanyID,))
    myresult = mycursor.fetchall()
    mydb.commit()
    mycursor.close()
    mydb.close()
    return myresult

def getHealthResults(CompanyID):
    mydb = mysql.connector.connect(host= hostName, user=databaseUser, passwd=databasePassword, database=databaseName)
    if(mydb):
        print("connection made")

    mycursor = mydb.cursor()
    query = ("SELECT * FROM HealthResults WHERE CompanyID = %s")
    mycursor.execute(query, (CompanyID,))
    myresult = mycursor.fetchall()
    mydb.commit()
    mycursor.close()
    mydb.close()
    return myresult

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
    tables['UserProfile'] = ("create table UserProfile(UserID varchar(10),Email varchar(50),Password varchar(30),FullName varchar(50),primary key(UserID));")
    tables['CompanyProfile']=("create table SentimentResults(CompanyID varchar(10),CompanyName varchar(10),primary key(CompanyID));")
    tables['SentimentResults'] = ("create table SentimentResults(CompanyID varchar(10), SentimentID varchar(10),SentimentResultsData varchar(100),SentimentDate DATE,primary key(SentimentID)foreign key(CompanyID) references UserProfile(CompanyProfile) on delete cascade);")
    tables['HealtResults'] = ("create table HealthResults(CompanyID varchar(10),HealthID varchar(10),HealthResultsData varchar(100), #requires reviewHealthDate DATE,primary key(HealthID)foreign key(CompanyID) references UserProfile(CompanyProfile) on delete cascade);")
    tables['PredictionResults'] = ("create table PredictionResults(CompanyID varchar(10),PredictionID varchar(10),PredictionResultsData varchar(100), #requires reviewPredictionDate DATE,primary key(PredictionID)foreign key(CompanyID) references UserProfile(CompanyProfile) on delete cascade);")
    tables['SearchHistory'] = ("create table SearchHistory(UserID varchar(10),SearchID varchar(10),SearchKeyword varchar(50), SearchDate DATE,primary key(SearchID)foreign key(UserID) references UserProfile(UserID) on delete cascade);")
    tables['NewsArticle'] = ("create table NewsArticle(ArticleID varchar(10),Title varchar(100),ThumbnailLink varchar(100),ArticleLink varchar(100),Description varchar(100), ArticleDate DATE,primary key(ArticleID)  );")

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

