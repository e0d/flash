(ns flash.printing)

(def separator "--------------------------")

(def forms [:form_1s :form_1p :form_2s :form_2p :form_3s :form_3p])

(def pronoun_1s "yo")
(def pronoun_1p "nosotras")
(def pronoun_2s "tú")
(def pronoun_2p "vosotras")
(def pronoun_3s "ella")
(def pronoun_3p "ellas")

(def padding (+ 2 (count pronoun_2p)))

(def pronouns {:form_1s pronoun_1s
               :form_1p pronoun_1p
               :form_2s pronoun_2s
               :form_2p pronoun_2p
               :form_3s pronoun_3s
               :form_3p pronoun_3p
               }
  )


(defn prn-verb-form
  [form verb]
  (let [
        pronoun (form pronouns)
        len (count pronoun)
        form (form verb)
        ]
    (println (str pronoun (format (str "%" ( - padding len) "s") "") form)))
    )

(defn prn-verb-forms
  [verb]
  (
   do
   (println)
   (println separator)
   (println (str (:infinitive verb) " | " (:pastparticiple verb) " | " (:gerund verb)))
   (println (str (:tense verb) " | " (:mood verb)))
   (println (:verb_english verb))
   (println separator)
   (map #(prn-verb-form % verb) forms)
   ))


(defn prn-verb-form-table
  [form verb]
  (let [
        pronoun (form pronouns)
        len (count pronoun)
        form (form verb)
        ]
    (println (str pronoun (format (str "%" ( - padding len) "s") "") form)))
    )

(defn prn-verb-forms-table
  [verb]
  (
   do
   (println)
   (println separator)
   (println (str (:infinitive verb) " | " (:pastparticiple verb) " | " (:gerund verb)))
   (println (str (:tense verb) " | " (:mood verb)))
   (println (:verb_english verb))
   (println separator)
   (map #(prn-verb-form % verb) forms)
   ))
