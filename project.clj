(defproject org.cyverse/mescal "4.1.1-SNAPSHOT"
  :description "A Clojure client library for the Tapis API."
  :url "https://github.com/cyverse-de/mescal"
  :license {:name "BSD Standard License"
            :url "https://cyverse.org/license"}
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  ;; Fail the build on a new dependency conflict rather than printing a
  ;; warning nobody reads.
  :pedantic? :abort
  ;; Records versions Leiningen already resolves, read off the resolved
  ;; classpath rather than copied from lein's "Consider using these
  ;; :managed-dependencies" hint -- that hint names the version that LOST the
  ;; conflict, so pasting it would be a silent upgrade. Most of these arbitrate
  ;; metosin/compojure-api 1.1.14, the final release of an archived project whose
  ;; transitives disagree with each other, and clj-http vs buddy-core.  ;;
  ;; The jackson-* entries align a family that the cheshire 6 upgrade split
  ;; (core/cbor/smile at 2.21.1, databind/annotations left at 2.18.3). Jackson
  ;; needs those to move together; :pedantic? cannot see it because each artifact
  ;; is individually unambiguous, only the family has drifted apart.
  :managed-dependencies [[com.fasterxml.jackson.core/jackson-annotations "2.21"]
                         [com.fasterxml.jackson.core/jackson-databind "2.21.1"]
                         [commons-codec "1.16.1"]
                         [prismatic/schema "1.1.12"]
                         [ring/ring-codec "1.1.0"]
                         [ring/ring-core "1.6.3"]]
  :dependencies [[org.clojure/clojure "1.12.5"]
                 [cheshire "6.2.0"]
                 [clj-http "3.13.1"]
                 [clj-time "0.15.2"]
                 [com.cemerick/url "0.1.1" :exclusions [com.cemerick/clojurescript.test]]
                 [medley "1.4.0"]
                 [me.raynes/fs "1.4.6"]
                 [org.cyverse/authy "3.0.2"]
                 [org.cyverse/clojure-commons "3.0.13"]
                 [org.cyverse/service-logging "2.8.6"]
                 [slingshot "0.12.2"]]
  :eastwood {:exclude-namespaces [mescal.de :test-paths]
             :linters [:wrong-arity :wrong-ns-form :wrong-pre-post :wrong-tag :misplaced-docstrings]}
  ;; cljfmt lives in its own profile: its tree and test2junit's disagree on which
  ;; Clojure to use (1.11.4/1.12.4 against 1.11.1), which trips :pedantic? :abort
  ;; on a conflict between two plugins that never reaches the runtime classpath.
  ;; Format with `lein with-profile +cljfmt cljfmt check`.
  :profiles {:cljfmt {:plugins [[dev.weavejester/lein-cljfmt "0.16.4"]]
                      :pedantic? :warn}
             :repl {:source-paths ["src" "repl/src"]
                    :resource-paths ["repl/resources"]}}
  :plugins [[jonase/eastwood "1.4.3"]
            [lein-ancient "1.0.0"]
            [test2junit "1.4.4"]])
