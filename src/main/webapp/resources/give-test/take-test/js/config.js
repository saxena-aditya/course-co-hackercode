/* file to store the url and other key values  */

let test_start_url;
let test_update_url;
let test_submit_ur;
let test_get_store_url;
let redirect_url;

const prod_test_start_url = "/get-test-data/";
const prod_test_update_url = "/update-test-data";
const prod_test_submit_url = "/get-result";
const prod_test_get_store_url = "localhost:3000/get";
const prod_redirect_url = "/admin-login";

const dev_test_start_url = "http://localhost:8080/WebHackerCode/get-test-data/";
const dev_test_update_url =
  "http://localhost:8080/WebHackerCode/update-test-data";
const dev_test_submit_url = "http://localhost:8080/WebHackerCode/get-result";
const dev_test_get_store_url = "localhost:3000/get";
const dev_redirect_url = "http://localhost:8080/WebHackerCode/dashboard";

const redirect_url_after_window_switch = "/profile";

const inProduction = true;

if (!inProduction) {
  test_start_url = dev_test_start_url;
  test_update_url = dev_test_update_url;
  test_submit_url = dev_test_submit_url;
  redirect_url = dev_redirect_url;
} else {
  test_start_url = prod_test_start_url;
  test_update_url = prod_test_update_url;
  test_submit_url = prod_test_submit_url;
  redirect_url = prod_redirect_url;
}
