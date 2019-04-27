(ns backstop-bucks.handler-spec
  (:require [backstop-bucks.handler :as handler]
            [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure.data.json :as json]
            [backstop-bucks.util.database-methods :refer :all]))

(defn test-get [req-type uri params]
  {:remote-addr "localhost"
   :headers {"host" "localhost"
             "content-type" "application/json"
             "accept" "application/json"}
   :server-port 80
   :content-type "application/json"
   :uri uri
   :server-name "localhost"
   :query-string nil
   ;:body (java.io.ByteArrayInputStream. (.getBytes (json/write-str params)))
   :scheme :http
   :request-method req-type})

;; let's assume we want to test some routes, app in the handler namespace
(deftest handler-test
  (testing "the default endpoint response"
    (let [response (handler/app-routes (test-get :get "/" {:item 1}))]
      (is (= "Hello World"
             (response :body)))))
  (testing "a POST request to the default endpoint"
    (let [response (handler/app-routes (test-get :post "/" {:item 1}))]
      (is (= 404
             (response :status)))))
  (testing "the user endpoint"
    (testing "with a get"
      (with-redefs [find-by-id (fn [x] {:name "Kirby" :_id 0})]
      (let [response (handler/app-routes (test-get :get "/user/0" {}))]
        (is (= 200
              (response :status)))
        (is (= {:name "Kirby" :_id 0}
               (response :body)))))))
  )
