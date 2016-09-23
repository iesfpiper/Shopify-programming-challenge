import json, urllib2

totalPrice = 0

#initialize web page information
pageNum = 1
webAddress = "http://www.shopicruit.myshopify.com/products.json?page="

#get web data, parse information and repeat until no more data
while True:
    print pageNum
    #set url for this iteration
    url = webAddress + `pageNum`
    print url
    #get web data
    webResponse = urllib2.urlopen(url)
    htmlData = webResponse.read()
    #parse htmlData (decode json format)
    jsonData = json.loads(htmlData)
    #verify data exists
    if not len(jsonData["products"]): 
        break
    #for each product and it's variants, add the price of each clock or watch
    #to the totalPrice variable
    for product in jsonData["products"]:
        if product["product_type"].lower() == "clock" or product["product_type"].lower() == "watch":
            for variant in product["variants"]:
                totalPrice += float(variant["price"])
                
    #increase page number for next iteration
    pageNum += 1
    
print totalPrice