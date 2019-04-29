(ns training-planner.core
  (:gen-class))

(def intensities (hash-map
                   :RI 10
                   :TR 8.5
                   :SSR 7
                   :ER 5.5
                   :RR 4.5
                   ))


(def intensity-tss (hash-map
                     1 20
                     2 30
                     3 40
                     4 50
                     5 60
                     6 70
                     7 80
                     8 100
                     9 120
                     10 140
                     ))

(defn TSS [run-type-durations]
  (let [running-interval (:RI run-type-durations)
        tempo (:TR run-type-durations)
        steady-state (:SSR run-type-durations)
        endurance (:ER run-type-durations)
        recovery (:RR run-type-durations)
        ]
  (println running-interval)
  ))

(defn -main
  "Creates a training plan"
  [& args]
  (TSS (hash-map :RI 1 :TR 2 :SSR 3 :ER 4 :RR 5))
  (println "Go run!!"))
