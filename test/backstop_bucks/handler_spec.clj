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
               (response :body))))))
    (testing "with a post"
      (with-redefs [create-user (fn [x y])]
        (let [response (handler/app-routes (test-get :post "/user" {:name "Link" :_id 1}))]
          (is (= 200
                 (response :status)))))))
    (testing "with a put"
      (testing "that successfully finds a user"
        (with-redefs [update-user (fn [x y])
                      find-by-id (fn [x] {:name "Link" :_id 1})]
          (let [response (handler/app-routes (test-get :put "/user/1" {:name "Marth" :_id 1}))]
            (is (= 200 (response :status))))))
      (testing "without finding a user"
        (with-redefs [update-user (fn [x y])
                      find-by-id (fn [x] [])]
          (let [response (handler/app-routes (test-get :put "/user/1" {:name "Marth" :_id 1}))]
            (is (= 404 (response :status)))))))
    (testing "with a delete"
      (with-redefs [delete-user (fn [x])]
        (let [response (handler/app-routes (test-get :delete "/user/1" {}))]
          (is (= 200 (response :status))))))

  (testing "the rewards endpoint"
    (testing "with a get"
      (with-redefs [find-rewards-by-id (fn [x] {:_id 0 :price "100" :reward-name "Wii" :reward-state "unredeemed"})]
        (let [response (handler/app-routes (test-get :get "/rewards/0" {}))]
          (is (= 200
                 (response :status)))
          (is (= {:_id 0 :price "100" :reward-name "Wii" :reward-state "unredeemed"}
                 (response :body))))))
    (testing "with a post"
      (with-redefs [create-rewards (fn [_ _ _ _])]
        (let [response (handler/app-routes (test-get :post "/rewards" {:_id 1 :price "10" :reward-name "Pen" :reward-state "redeemed"}))]
          (is (= 200
                 (response :status))))))
    (testing "with a put"
      (testing "that successfully finds a reward"
        (with-redefs [update-rewards (fn [_ _ _ _])
                      find-rewards-by-id (fn [x] {:_id 1 :price "10" :reward-name "Pen" :reward-state "redeemed"})]
          (let [response (handler/app-routes (test-get :put "/rewards/1" {:_id 2 :price "5" :reward-name "Sticker" :reward-state "redeemed"}))]
            (is (= 200 (response :status))))))
      (testing "without finding a reward"
        (with-redefs [update-rewards (fn [_ _ _ _])
                      find-rewards-by-id (fn [x] [])]
          (let [response (handler/app-routes (test-get :put "/rewards/1" {:_id 2 :price "5" :reward-name "Sticker" :reward-state "redeemed"}))]
            (is (= 404 (response :status)))))))
    (testing "with a delete"
      (with-redefs [delete-rewards (fn [x])]
        (let [response (handler/app-routes (test-get :delete "/rewards/1" {}))]
          (is (= 200 (response :status)))))))
  )
