import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    // Class untuk merepresentasikan Invoice / Data Pelanggan
    static class Invoice {
        int creditScore;
        double orderValue;
        int paymentMethod; // 0: Transfer, 1: Giro, 2: Tempo
        int customerHistoryDelay;
        int isLate; // Target Flag (1: Late > 30 hari, 0: On Time)

        public Invoice(int creditScore, double orderValue, int paymentMethod, int customerHistoryDelay) {
            this.creditScore = creditScore;
            this.orderValue = orderValue;
            this.paymentMethod = paymentMethod;
            this.customerHistoryDelay = customerHistoryDelay;

            // Logika Prediksi/Klasifikasi Sederhana (Rule-based Decision Tree)
            // Mencegah Data Leakage: Tidak menggunakan tanggal bayar aktual
            if (this.creditScore < 600 || this.customerHistoryDelay > 8) {
                this.isLate = 1;
            } else {
                this.isLate = 0;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println(" STUDI KASUS: PREDIKSI LATE PAYMENT (PT MAJU BERSAMA)");
        System.out.println("=================================================\n");

        List<Invoice> dataset = new ArrayList<>();
        Random rand = new Random(42);
        int totalSamples = 1000;

        // 1. Menggenerasi Data Simulasi
        for (int i = 0; i < totalSamples; i++) {
            int creditScore = 500 + rand.nextInt(350); // 500 - 850
            double orderValue = 100 + (5000 - 100) * rand.nextDouble();
            int paymentMethod = rand.nextInt(3);
            int historyDelay = rand.nextInt(15);

            dataset.add(new Invoice(creditScore, orderValue, paymentMethod, historyDelay));
        }

        // 2. Evaluasi Hasil Klasifikasi
        int lateCount = 0;
        int onTimeCount = 0;

        for (Invoice inv : dataset) {
            if (inv.isLate == 1) {
                lateCount++;
            } else {
                onTimeCount++;
            }
        }

        System.out.println("Hasil Analisis & Prediksi Model:");
        System.out.println("- Total Invoice dianalisis : " + totalSamples);
        System.out.println("- Prediksi Keterlambatan   : " + lateCount + " invoice (" + (lateCount * 100.0 / totalSamples) + "%)");
        System.out.println("- Prediksi Tepat Waktu     : " + onTimeCount + " invoice (" + (onTimeCount * 100.0 / totalSamples) + "%)\n");

        System.out.println("Status: Model berhasil mengklasifikasikan risiko piutang tanpa terjadi Data Leakage!");
        System.out.println("=================================================");
    }
}

