(ns flash.core
  (:use
   [flash.printing]
   )
  (:require
   [clojure.data.csv :as csv]
   [clojure.java.io :as io]
   [yaml.core :as yml]
   [flatland.ordered.set :refer [ordered-set]]
   [flatland.ordered.map :refer [ordered-map]]
   )         
  )

(def verb-db "/home/edward/Documents/git/e0d/fred-jehle-spanish-verbs/jehle_verb_database.csv")

;; I've hacked the file to work around a strange bug wherein the first keyword
;; contains leading and trailing escaped double quote characters.
;; e.g., :"infinitive"
;; I've added an empty leading field to the csv as a workaround until \\I have time to grok the
;; csv source code.
(def verbs
  (with-open [reader (io/reader verb-db)]
    (doall
     (csv/read-csv reader))))

(defn csv->seq-of-maps [verbs]
  (map zipmap
       (->> (first verbs) ;; First row is the header
            (map keyword) ;; Drop if you want string keys instead
            repeat)
       (rest verbs)))

(def verb-seq-of-maps (csv->seq-of-maps verbs))


(defn find-verb
  [infinitive]
  (filter (comp #{infinitive} :infinitive) verb-seq-of-maps))

(defn find-verb-by-tense-and-mood
  [infinitive mood tense]
  (first
  (sequence (comp (filter (comp #{infinitive} :infinitive))
                  (filter (comp #{mood} :mood))
                  (filter (comp #{tense} :tense))
                  )
            verb-seq-of-maps)))


(def tenses (into (ordered-set) (map #(:tense %) (find-verb "ir"))))

(def all-infinitives (into (ordered-set) (map #(:infinitive %) verb-seq-of-maps)))

(defn all-forms [infinitive]
  (map prn-verb-forms (find-verb infinitive)))

-- Good for flowing into the online formatting tool
(prn (yml/generate-string o :dumper-options {:flow-style :flow :scalar-style :plain}))


(defn tense-name
  [tense mood]
  (str (clojure.string/lower-case tense)
       " "
       (clojure.string/lower-case mood)))

(defn tense-name
  [tense mood]
  (str tense
       " "
       mood))


(defn row-to-ordered-map
  [row]
  (ordered-map (keyword (tense-name (:tense row) (:mood row))) (ordered-map :names (ordered-map :en (:tense_english row)
                                                                                                :es (:tense row))
                                                                            :moods (ordered-map :en (:mood_english row)
                                                                                                :es (:mood row))
                                                                            :samples (ordered-map :en (:verb_english row))
                                                                            :1s (:form_1s row)
                                                                            :1p (:form_1p row)
                                                                            :2s (:form_2s row)
                                                                            :2p (:form_2p row)
                                                                            :3s (:form_3s row)
                                                                            :3p (:form_3p row))
                                                                            ))

(defn get-structured-verb
  [all-forms]
  (let
      [head (first all-forms)]
    (ordered-map
     (keyword (:infinitive head)) (ordered-map
                                   :infinitive (:infinitive head)
                                   :gerund (:gerund head)
                                   :pastparticiple (:pastparticiple head)
                                   :definitions (ordered-map
                                                 :en (:infinitive_english head)
                                                 )
                                   :tenses (map row-to-ordered-map all-forms))
     )))


(defn write-verb
  [all-forms]
  (let
      [
       verb (get-structured-verb all-forms)
       infinitive (:infinitive (first all-forms))
       ]
    (spit (str "/tmp/" infinitive ".yaml") (yml/generate-string verb))))

(defn write-verb
  [all-forms]
  (let
      [
       verb (get-structured-verb all-forms)
       infinitive (:infinitive (first all-forms))
       ]
    (str "resources/verbs/" infinitive ".yaml")))

(map write-verb (map find-verb (sort all-infinitives)))

(write-verb ( find-verb (first all-infinitives)))

(def verb (get-structured-verb (find-verb "echar")))

(spit "/tmp/test.yaml" (yml/generate-string verb))

