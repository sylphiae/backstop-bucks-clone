(ns backstop-bucks.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [backstop-bucks.endpoints.user-endpoint :refer [get-user create-user update-user delete-user]]
            [backstop-bucks.endpoints.user-rewards-endpoint :refer [get-rewards get-all-rewards create-rewards update-rewards delete-rewards]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
            [ring.middleware.cors :refer [wrap-cors]]
            ;[ring.middleware.reload :refer [wrap-reload]]
            ))

(defn is-authenticated [{user :user :as req}]
  (not (nil? user)))

(defroutes app-routes
           (GET "/" [] "Hello World")
           (GET "/user/:id" [id req] get-user)
           (POST "/user" req create-user)
           (PUT "/user/:id" [id req] update-user)
           (DELETE "/user/:id" [id req] delete-user)

           (GET "/rewards/:id" [id req] get-rewards)
           (GET "/rewards" [req] get-all-rewards)
           (POST "/rewards" req create-rewards)
           (PUT "/rewards/:id" [id req] update-rewards)
           (DELETE "/rewards/:id" [id req] delete-rewards)


           (route/not-found "Not Found"))

(def app (-> #'app-routes
             (wrap-json-response)
             (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
             (wrap-json-params)
             ;(wrap-reload)
             (wrap-cors :access-control-allow-origin #"http://localhost:3449"
                        :access-control-allow-methods [:get :put :post :delete]
                        :access-control-allow-headers ["Origin" "X-Requested-With" "Content-Type" "Accept"] )))

;; want information to parse as JSON automatically

