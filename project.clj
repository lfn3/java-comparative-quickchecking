(defproject java-comparative-quickchecking "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [junit/junit "4.12"]
                 [com.pholser/junit-quickcheck-core "0.7"]
                 [com.pholser/junit-quickcheck-generators "0.7"]
                 [org.quicktheories/quicktheories "0.14"]
                 [Hypothesis/Hypothesis "0.0.1-SNAPSHOT"]]
  :source-paths []
  :test-paths ["src/test/java"]
  :java-source-paths ["src/main/java"])
