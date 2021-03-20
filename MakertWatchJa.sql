/*

Database Design Notes


-----tables------

UserProfile table
	-UserID
	-Email
	-Password
	-FullName

SentimentResults table
	-UserID
	-SentimentID
	-SentimentResultsData
	-SentimentDate


HealthResults table
	-UserID
	-HealthID
	-HealthResultsData
	-HealthDate


PredictionResults table
	-UserID
	-PredictionID
	-PredictionResultsData
	-PredictionDate


SearchHistory table
	-UserID
	-SearchID
	-SearchKeyword
	-SearchDate


NewArticle table
	-ArticleID
	-Title
	-ThumbnailLink
	-ArticleLink
	-Description
	-ArticleDate

*/



/* SQL CODE BEGINS HERE*/

/*

CREATE DATABASE IF NOT EXISTS MarketWatchJamaicaDB;
USE MarketWatchJamaicaDB;

*/


create table UserProfile(
    UserID varchar(10),
    Email varchar(50),
    Password varchar(30),
    FullName varchar(50),
    primary key(UserID)
    
);


create table SentimentResults(
    UserID varchar(10),
    SentimentID varchar(10),
    SentimentResultsData varchar(100),
    SentimentDate DATE,
    primary key(SentimentID)
    foreign key(UserID) references UserProfile(UserID) on delete cascade
    
);



create table HealthResults(
    UserID varchar(10),
    HealthID varchar(10),
    HealthResultsData varchar(100), #requires review
    HealthDate DATE,
    primary key(HealthID)
    foreign key(UserID) references UserProfile(UserID) on delete cascade
    
);



create table PredictionResults(
    UserID varchar(10),
    PredictionID varchar(10),
    PredictionResultsData varchar(100), #requires review
    PredictionDate DATE,
    primary key(PredictionID)
    foreign key(UserID) references UserProfile(UserID) on delete cascade
    
);




create table SearchHistory(
    UserID varchar(10),
    SearchID varchar(10),
    SearchKeyword varchar(50), 
    SearchDate DATE,
    primary key(SearchID)
    foreign key(UserID) references UserProfile(UserID) on delete cascade
    
);




create table NewArticle(
    ArticleID varchar(10),
    Title varchar(100),
    ThumbnailLink varchar(100),
    ArticleLink varchar(100),
    Description varchar(100), 
    ArticleDate DATE,
    primary key(ArticleID)  
    
);
