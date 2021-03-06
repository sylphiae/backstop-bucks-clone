(defproject backstop-bucks "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [re-frame "0.10.5"]
                 [baking-soda "0.2.0"]
                 [reagent "0.8.1" :exclusions [cljsjs/react
                                               cljsjs/react-dom]]
                 [cljsjs/react "16.3.2-0"]
                 [cljsjs/react-dom "16.3.2-0"]
                 [cljsjs/react-transition-group "2.3.1-0"]
                 [cljsjs/react-popper "0.10.4-0"]

                 [com.novemberain/monger "3.1.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-jetty-adapter "1.6.3"]

                 [clojure-future-spec "1.9.0-beta4"]
                 [org.clojure/test.check "0.9.0"]
                 [circleci/bond "0.3.0"]
                 [cloverage "1.0.9"]

                 [day8.re-frame/http-fx "0.1.6"]
                 [ring-cors "0.1.13"]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-ring "0.12.4"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj" "src/cljs"]

  :main backstop-bucks.core

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :minify-assets
  {:assets
   {"resources/public/css/site.min.css" "resources/public/css/spacelab.css"}}

  :figwheel {:css-dirs ["resources/public/css"]
             :ring-handler backstop-bucks.handler/app
             :server-port 3449
             :nrepl-port 7200
             :nrepl-middleware ["cemerick.piggieback/wrap-cljs-repl"]
             :http-server-root "public"}
  :ring {:handler backstop-bucks.handler/app}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.9.10"]
                   [cljsjs/react-bootstrap "0.31.5-0"]
                   [javax.servlet/servlet-api "2.5"]
                   [ring/ring-mock "0.3.2"]
                   [com.cemerick/piggieback "0.2.2"]]

    :plugins      [[lein-cljsbuild "1.1.1"]
                   [lein-figwheel "0.5.16"]
                   [lein-cljfmt "0.6.1"]]}
   :prod { }
   }

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "backstop-bucks.core/mount-root"}
     :compiler     {:main                 backstop-bucks.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}
                    }}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            backstop-bucks.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}


    ]}
  )
