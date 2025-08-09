/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.utils;

import application.models.PredictionResultModel;
import java.util.*;

/**
 *
 * @author mhdja
 */
public class NaiveBayesAlgoritma {
     private List<DataItem> trainingData;
     private Set<String> labels = new HashSet<>();
     private Map<String, Integer> labelCount = new HashMap<>();
     private Map<String, Map<String, Map<String, Integer>>> featureCount = new HashMap<>();
     
     public NaiveBayesAlgoritma(List<DataItem> trainingData) {
        this.trainingData = trainingData;
        train();
     }
     
     private void train() {
        System.out.println("=== Mulai Training Data ===");
        for (DataItem item : trainingData) {
            String label = item.label;
            labels.add(label);
            labelCount.put(label, labelCount.getOrDefault(label, 0) + 1);

            System.out.println("Label ditemukan: " + label + " (total sementara: " + labelCount.get(label) + ")");

            for (Map.Entry<String, String> entry : item.features.entrySet()) {
                String feature = entry.getKey();
                String value = entry.getValue();

                featureCount
                    .computeIfAbsent(label, k -> new HashMap<>())
                    .computeIfAbsent(feature, k -> new HashMap<>())
                    .put(value, featureCount.get(label).get(feature).getOrDefault(value, 0) + 1);

                System.out.println("  Feature: " + feature + " = " + value +
                    " (count: " + featureCount.get(label).get(feature).get(value) + ")");
            }
        }
        System.out.println("=== Selesai Training ===\n");
    }
       
   public String predict(Map<String, String> features) {
    System.out.println("Features received:");
    for (Map.Entry<String, String> entry : features.entrySet()) {
        System.out.println("  " + entry.getKey() + " : " + entry.getValue());
    }

    double maxProb = -1;
    String bestLabel = null;

    for (String label : labels) {
        double prob = getPrior(label);
        System.out.println("Label: " + label + ", Prior: " + prob);

        for (Map.Entry<String, String> entry : features.entrySet()) {
            String feature = entry.getKey();
            String value = entry.getValue();
            double likelihood = getLikelihood(label, feature, value);
//            prob *= likelihood;

            System.out.println("  Feature: " + feature + ", Value: " + value + ", Likelihood: " + 20 + ", Prob so far: " + prob);
        }

        System.out.println("Total probability for label " + label + " = " + prob);

        if (prob > maxProb) {
            maxProb = prob;
            bestLabel = label;
        }
    }

    System.out.println("Predicted label: " + bestLabel + " with probability " + maxProb);
    return bestLabel;
}


    
    public PredictionResultModel predictWithExplanation(Map<String, String> features) {
        double maxProb = -1;
        String bestLabel = null;

        StringBuilder sb = new StringBuilder();
        sb.append("=== Perhitungan Naive Bayes ===\n");
        System.out.println("=== Perhitungan Naive Bayes ===");

        for (String label : labels) {
            double prior = getPrior(label);
            double prob = prior;

            sb.append("\nLabel: ").append(label).append("\n");
            sb.append("Prior(").append(label).append(") = ").append(prior).append("\n");
            System.out.println("\nLabel: " + label);
            System.out.println("Prior(" + label + ") = " + prior);

            for (Map.Entry<String, String> entry : features.entrySet()) {
                String feature = entry.getKey();
                String value = entry.getValue();
                double likelihood = getLikelihood(label, feature, value);
                prob *= likelihood;

                sb.append("Likelihood(")
                  .append(feature).append("=").append(value)
                  .append(" | ").append(label)
                  .append(") = ").append(likelihood).append("\n");

                System.out.println("Likelihood(" + feature + "=" + value + " | " + label + ") = " + likelihood);
            }

            sb.append("Total probability untuk label '").append(label).append("' = ").append(prob).append("\n");
            System.out.println("Total probability untuk label '" + label + "' = " + prob);

            if (prob > maxProb) {
                maxProb = prob;
                bestLabel = label;
            }
        }

        sb.append("\n>> Prediksi terbaik: ").append(bestLabel)
          .append(" (Probabilitas: ").append(maxProb).append(")");
        System.out.println(">> Prediksi terbaik: " + bestLabel + " (Probabilitas: " + maxProb + ")");
        System.out.println("=== Selesai Perhitungan ===");

        return new PredictionResultModel(bestLabel, sb.toString());
    }

    
    private double getPrior(String label) {
        return (double) labelCount.get(label) / trainingData.size();
    }

    private double getLikelihood(String label, String feature, String value) {
        int count = 0;
        
        System.out.println("getLikelihood Debug -> Label: " + label + ", Feature: " + feature + ", Value: " + value);
        
        
        if (featureCount.containsKey(label) &&
            featureCount.get(label).containsKey(feature) &&
            featureCount.get(label).get(feature).containsKey(value)) {
            count = featureCount.get(label).get(feature).get(value);
        }

        int total = featureCount.get(label).get(feature).values().stream().mapToInt(i -> i).sum();
        
        System.out.print(total);

        // Laplace smoothing
        double likelihood = (count + 1.0) / (total + featureCount.get(label).get(feature).size());
        
        return likelihood;
    }
    
    public Map<String, Double> predictProbabilities(Map<String, String> features) {
        Map<String, Double> labelProbs = new HashMap<>();

        // Hitung numerator untuk semua label
        double totalProb = 0;
        for (String label : labels) {
            double prob = getPrior(label);

            for (Map.Entry<String, String> entry : features.entrySet()) {
                String feature = entry.getKey();
                String value = entry.getValue();
                prob *= getLikelihood(label, feature, value);
            }

            labelProbs.put(label, prob);
            totalProb += prob;
        }

        // Normalisasi
        for (String label : labelProbs.keySet()) {
            double normalized = labelProbs.get(label) / totalProb;
            labelProbs.put(label, normalized);
        }

        return labelProbs;
    }
    
    public Map<String, Double> getLabelProbabilities(List<DataItem> data) {
        Map<String, Integer> labelCounts = new HashMap<>();
        int total = data.size();

        for (DataItem item : data) {
            String label = item.getLabel();
            labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);
        }

        Map<String, Double> probabilities = new HashMap<>();
        for (Map.Entry<String, Integer> entry : labelCounts.entrySet()) {
            probabilities.put(entry.getKey(), (double) entry.getValue() / total);
        }

        return probabilities;
    }
    
    public Map<String, Map<String, Double>> getFeatureProbabilitiesPerLabel(String label) {
        Map<String, Map<String, Double>> result = new HashMap<>();

        if (!featureCount.containsKey(label)) return result;

        // Ambil semua fitur dan semua nilai yang pernah muncul (seluruh fitur global)
        Set<String> allFeatures = new HashSet<>();
        Map<String, Set<String>> allFeatureValues = new HashMap<>();

        for (Map<String, Map<String, Integer>> labelMap : featureCount.values()) {
            for (String feature : labelMap.keySet()) {
                allFeatures.add(feature);
                allFeatureValues.putIfAbsent(feature, new HashSet<>());
                allFeatureValues.get(feature).addAll(labelMap.get(feature).keySet());
            }
        }

        for (String feature : allFeatures) {
            Map<String, Double> valueProbs = new HashMap<>();

            Map<String, Integer> valueCounts = featureCount
                    .getOrDefault(label, Collections.emptyMap())
                    .getOrDefault(feature, Collections.emptyMap());

            int total = valueCounts.values().stream().mapToInt(i -> i).sum();

            for (String value : allFeatureValues.get(feature)) {
                int count = valueCounts.getOrDefault(value, 0);
                double prob = total > 0 ? (double) count / total : 0.0;
                valueProbs.put(value, prob);
            }

            result.put(feature, valueProbs);
        }

        return result;
    }


}
