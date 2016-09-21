# Shopify-programming-challenge
Various solutions to shopify's 2017 Winternship programming challenge question

I have decided to code multiple solutions to the programming challenge of calculating the total price of all items on the website http://shopicruit.myshopify.com/

- PHP only
  - This uses only PHP to gather the data, process it and display the total as well as a table with the individual price of each item and all of it's variants.  
- PHP and javascript
  - this uses PHP to get the JSON data from the shopicruit website.  Javascript uses an ajax request to get the JSON data from my PHP file and then processes it.  
  - The code looks a little dirtier than the PHP only code, however this would allow me to add filters to the items used to calculate the total price, and generally make the web page's content dynamic
- Java
  - This source code uses standard java libraries and the org.json library which was downloaded from somewhere (I forget now).  I will include the jar file in my repository.  I see no benefit to coding this in java except to demonstrate that I solved the problem of programming it in java code.  
