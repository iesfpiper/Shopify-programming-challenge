#include <jansson.h>
#include <curl/curl.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#define BUFFER_SIZE  (256 * 1024)  /* 256 KB */

struct write_result
{
    char *data;
    int pos;
};

static size_t write_response(void *ptr, size_t size, size_t nmemb, void *stream)
{
    struct write_result *result = (struct write_result *)stream;

    if(result->pos + size * nmemb >= BUFFER_SIZE - 1)
    {
        fprintf(stderr, "error: too small buffer\n");
        return 0;
    }

    memcpy(result->data + result->pos, ptr, size * nmemb);
    result->pos += size * nmemb;

    return size * nmemb;
}

static char *request(const char *url)
{
    CURL *curl = NULL;
    CURLcode status;
    struct curl_slist *headers = NULL;
    char *data = NULL;
    long code;

    curl_global_init(CURL_GLOBAL_ALL);
    curl = curl_easy_init();
    if(!curl)
        goto error;

    data = malloc(BUFFER_SIZE);
    if(!data)
        goto error;

    struct write_result write_result = {
        .data = data,
        .pos = 0
    };

    curl_easy_setopt(curl, CURLOPT_URL, url);

    /* GitHub commits API v3 requires a User-Agent header */
    headers = curl_slist_append(headers, "User-Agent: Jansson-Tutorial");
    curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headers);

    curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, write_response);
    curl_easy_setopt(curl, CURLOPT_WRITEDATA, &write_result);

    status = curl_easy_perform(curl);
    if(status != 0)
    {
        fprintf(stderr, "error: unable to request data from %s:\n", url);
        fprintf(stderr, "%s\n", curl_easy_strerror(status));
        goto error;
    }

    curl_easy_getinfo(curl, CURLINFO_RESPONSE_CODE, &code);
    if(code != 200)
    {
        fprintf(stderr, "error: server responded with code %ld\n", code);
        goto error;
    }

    curl_easy_cleanup(curl);
    curl_slist_free_all(headers);
    curl_global_cleanup();

    /* zero-terminate the result */
    data[write_result.pos] = '\0';

    return data;

error:
    if(data)
        free(data);
    if(curl)
        curl_easy_cleanup(curl);
    if(headers)
        curl_slist_free_all(headers);
    curl_global_cleanup();
    return NULL;
}

int main(void){
	int i, j;
	char* text;
	json_t *root;
	json_error_t error;
	
	json_t * products; //products array
	json_t * product;
	json_t * prodType;
	const char * strProdType;
	json_t * variants;
	json_t * variant;
	json_t * price;
	double dblPrice; 
	const char* strPrice;
	const char* strTitle;
	
	char * urlBlank = "http://shopicruit.myshopify.com/products.json?page=";
	int page = 2;
	char strPage[3];
	char url[1000];
	
	sprintf(strPage, "%d", page);
	strcpy(url, urlBlank);
	strcat(url, strPage);
	strcpy(url, urlBlank);
	strcat(url, strPage);
	
	printf("%s", urlBlank);
	printf("\n%s\n", url);
	
	text = request(url);
	printf("\n%s\n", text);
	root = json_loads(text, 0, &error);
	
	/*for reasons including how motivated I am, and perhaps it being the best choice given
	 * the programming problem, I am chosing to grab each page and process it before grabbing
	 * the next page
	 */
	products = json_object_get(root, "products");
	product = json_array_get(products, 0);
	prodType = json_object_get(product, "product_type");
	strProdType = json_string_value(prodType);
	printf("%s", strProdType);
	variants = json_object_get(product, "variants");
	variant = json_array_get(variants, 0);
	price = json_object_get(variant, "price");
	strPrice = json_string_value(price);
	printf("\n%s\n", strPrice);
	
	sscanf(strPrice, "%lf", &dblPrice);
	printf("price is %f", dblPrice);
	return 0;
}