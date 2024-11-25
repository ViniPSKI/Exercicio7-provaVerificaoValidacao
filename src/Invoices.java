/*
 * Regras: A função irá receber uma lista de produtos e um percentual de desconto.
 * O percentual de desconto deve ser aplicado ao TOTAL da fatura.
 * O retorno deve ser o total da fatura com o desconto aplicado.
 * 
 * Outras regras: Se o valor total da fatura ANTES da aplicação do desconto for maior que R$ 1.000,00, então deverá ser aplicado um desconto adicional de R$ 100,00
 * Se algum produto tiver preço ou quantidade negativa, a função deve lançar uma exceção (InvalidProductException)
 * 
 */

import java.util.List;

public class Invoices {

    public float calculateInvoice(List<Product> products, float discount) {
        float totalInvoice = 0;
        float aditionalDiscount = 0;
        float dicountApplicable = 0;

        if (!products.isEmpty()) {
            for (Product product : products) {
                if (product.getPrice() < 0 || product.getQuantity() < 0) {
                    throw new InvalidProductException(
                            "Preço ou quatidade negativos! Apenas é permitido valores positivos.");
                }
                totalInvoice += (product.getPrice() * product.getQuantity());
            }

            if (totalInvoice > 1000)
                aditionalDiscount = 100;
            
            dicountApplicable = discount / 100;

            totalInvoice = (totalInvoice - (totalInvoice * dicountApplicable)) - aditionalDiscount;
        }

        return totalInvoice;
    }

}
