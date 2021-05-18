import tweepy
from textblob import TextBlob
import pandas as pd
import numpy as np
import regex as re


consumerapikey = 'F7DOjaJnvB2NcF8vpGOo4KAvv'
consumerapisecretkey = 'ejqdkCCcLRSFSpuIYD88dJoqH33eRU5x9t62OJ8zC75x1Ufktw'
bearertoken = 'AAAAAAAAAAAAAAAAAAAAAKNDCgEAAAAAZlDlgmS9r0X8%2BqLkDRPZDyhXIQg%3DznSj9HZMnJKoJZPrN0BwXLCI1c16oGDOsX4VMqAXYn67eBos5c'
accesstoken = '1128017815301251073-QYKjhDDIxZF8UlzODcKHEWu1b63ldx'
secretaccesstoken = 'HHgAiV80lPb9NuF8qqApUx981uqAzbc5v0nnQRzf828A8'



def pullTweets(key):


    # Twitter API connection made and authorized
    verifyaccess =  tweepy.OAuthHandler(consumerapikey,consumerapisecretkey)
    verifyaccess.set_access_token(accesstoken,secretaccesstoken)
    myapi = tweepy.API(verifyaccess, wait_on_rate_limit=True)

    company = key 
    tweets = tweepy.Cursor(myapi.search, q=company, since='2021-04-04', lang = 'en').items(1000)

    

    mytweets = [[tweet.text, tweet.created_at] for tweet in tweets]
    tweetdf = pd.DataFrame(data=mytweets, columns=['Tweets', 'created_at'])
    tweetdf['Tweets'] = tweetdf['Tweets'].apply(cleantweets)
    tweetdf  = tweetdf.dropna()

    tweetdf['subjectivity'] = tweetdf['Tweets'].apply(getsubjectivity)

    tweetdf['polarity'] = tweetdf['Tweets'].apply(getpolarity)

    tweetdf['polarityAnalysis'] = tweetdf['polarity'].apply(getPolarityAnalysis)

    tweetdf['subjectivityAnalysis'] = tweetdf['subjectivity'].apply(getSubjectivityAnalysis)
    
    finaldata = getfinalAnalysis(tweetdf['polarityAnalysis'], tweetdf['subjectivityAnalysis'])


    print('The percentage of positive views include: ' + str((finaldata[0]/finaldata[3])*100)+' %')
    print('The percentage of negative views include: ' + str((finaldata[1]/finaldata[3])*100)+' %')
    print('The percentage of neutral views include: ' + str((finaldata[2]/finaldata[3])*100)+' %')

    print('The percentage of subjective views include: ' + str((finaldata[5]/finaldata[3])*100)+' %')
    print('The percentage of objective views include: ' + str((finaldata[4]/finaldata[3])*100)+' %')

    
    return finaldata

   

    





def cleantweets(text):
    text = re.sub(r'@[A-Za-z0-9]+','',text)
    text = re.sub(r'#','',text)
    text = re.sub(r'RT[\s]+','',text)
    text = re.sub(r'https?:\/\/\S+','', text)
    return text





def getsubjectivity(text):
    return TextBlob(text).sentiment.subjectivity

def getpolarity(text):
    return TextBlob(text).sentiment.polarity



def getPolarityAnalysis(score):
    if score > 0:
        return 'Positive'
    elif score == 0:
        return 'Neutral'
    else:
        return 'Negative'



def getSubjectivityAnalysis(score):
    if score > 0.5:
        return 'subjective'
    else:
        return 'objective'




def getfinalAnalysis(poldata, subdata):
    posVal = 0
    negVal = 0
    nueVal = 0
    ptotal = 0
    objVal = 0
    subVal = 0
    stotal = 0
    for pol in poldata:
        ptotal = ptotal + 1
        if pol == 'Positive':
            posVal = posVal+1

        if pol == 'Negative':
            negVal = negVal + 1

        if pol == 'Neutral':
            nueVal = nueVal + 1
  
    for sub in subdata:
        stotal = stotal + 1
        if sub =='subjective':
            subVal = subVal + 1
        if sub == 'objective':
            objVal = objVal + 1

  
    return [posVal, negVal, nueVal, ptotal, objVal, subVal, stotal]
    
