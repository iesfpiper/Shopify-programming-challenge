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
        foreach($variants in $product.variants){
            foreach($variant in $variants){
                $variant.price
                $totalPrice += $variant.price
            }
        }
    }
}

 "Total price `$$totalPrice"