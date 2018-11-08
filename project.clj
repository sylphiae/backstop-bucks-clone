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
                 [cljsjs/react-popper "0.10.4-0"]]

  :plugins [[lein-cljsbuild "1.1.7"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj" "src/cljs"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :minify-assets
  {:assets
   {"resources/public/css/site.min.css" "resources/public/css/spacelab.css"}}

  :figwheel {:css-dirs ["resources/public/css"]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.9.10"]
                   [cljsjs/react-bootstrap "0.31.5-0"]]

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
