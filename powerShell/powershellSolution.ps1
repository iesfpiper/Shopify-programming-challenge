$webDataArray = @()
$totalPrice = 0
$pageNum = 1
$webRequest = Invoke-WebRequest -uri http://shopicruit.myshopify.com/products.json?page=$pageNum
$jsonData = $webRequest.content
$extractedData = ConvertFrom-Json $jsonData
while($extractedData.products.length -gt 0){
    $webDataArray += $extractedData
    $pageNum++
    $webRequest = Invoke-WebRequest -uri http://shopicruit.myshopify.com/products.json?page=$pageNum
    $jsonData = $webRequest.content
    $extractedData = ConvertFrom-Json $jsonData
}

foreach($page in $webDataArray){
    foreach($product in $page.products){
    if(($product.product_type).toLower().compareTo("clock") -eq 0 -or ($product.product_type).toLower().compareTo("watch") -eq 0){
            foreach($variants in $product.variants){
                foreach($variant in $variants){
                    $variant.price
                    $totalPrice += $variant.price
                }
            }
        }
    }
}

 "Total price `$$totalPrice"