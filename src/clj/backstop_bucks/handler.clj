(ns backstop-bucks.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [backstop-bucks.endpoints.user-endpoint :refer [get-user create-user update-user delete-user]]))

(defn is-authenticated [{user :user :as req}]
  (not (nil? user)))

(defroutes app-routes
           (GET "/" [] "Hello World")
           (GET "/user/:id" [id req] get-user)
           (POST "/user" req create-user)
           (PUT "/user/:id" [id req] update-user)
           (DELETE "/user/:id" [id req] delete-user)


           (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

;; want information to parse as JSON automatically

