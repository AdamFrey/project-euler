(set-env!
  :dependencies
  '[[org.clojure/clojure "1.9.0-alpha15"]
    [criterium "0.4.4"]
    [org.clojure/data.int-map "0.2.4"]
    [metosin/boot-alt-test "0.3.1" :scope "test"]]
  :source-paths #{"src" "test"})

(require
  '[metosin.boot-alt-test :refer [alt-test]])

(deftask run-tests []
  (alt-test))

