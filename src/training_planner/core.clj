(ns training-planner.core
  (:gen-class))

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
