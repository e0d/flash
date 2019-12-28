(defproject flash "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.csv "0.1.4"]
                 [clj-commons/clj-yaml "0.7.0"]
                 [io.forward/yaml "1.0.9"]
                 [org.flatland/ordered "1.5.7"]
                 [cljstache "2.0.4"]
                 ]
  :repl-options {:init-ns flash.core}
  :java-cmd "/usr/java/latest/bin/java"

  )
