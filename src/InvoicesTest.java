/*
 * Regras: A função irá receber uma lista de produtos e um percentual de desconto.
 * O percentual de desconto deve ser aplicado ao TOTAL da fatura.
 * O retorno deve ser o total da fatura com o desconto aplicado.
 * 
 * Outras regras: Se o valor total da fatura antes da aplicação do desconto for maior que R$ 1.000,00, então deverá ser aplicado um desconto adicional de R$ 100,00
 * Se algum produto tiver preço ou quantidade negativa, a função deve lançar uma exceçã (InvalidProductException)
 * 
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

public class InvoicesTest {

    static Invoices invoices = new Invoices();

    static void setUp(){
        invoices = new Invoices();
    }

    @Test
    void testTotalComDescontoAbaixoDeMil(){
        List<Product> products = List.of(
            new Product("Product 1", (float) 50.0, 5),
            new Product("Product 2", (float) 20.0, 3),
            new Product("Product 3", (float) 150.0, 1)
        );

        float totalInvoice = invoices.calculateInvoice(products, 10); //460 - 10% = 414

        assertEquals(414, totalInvoice);
    }

    @Test
    void testTotalComDescontoExcedendoMil(){
        List<Product> products = List.of(
            new Product("Product 1", (float) 100.0, 5),
            new Product("Product 2", (float) 200.0, 3),
            new Product("Product 3", (float) 20.0, 1)
        );

        float totalInvoice = invoices.calculateInvoice(products, 5); //1120 - 5% = 1064 - 100 = 964

        assertEquals(964, totalInvoice);
    }

    @Test
    void testTotalComDescontoTotalDeMil(){
        List<Product> products = List.of(
            new Product("Product 1", (float) 500.0, 1),
            new Product("Product 2", (float) 500.0, 1)
        );

        float totalInvoice = invoices.calculateInvoice(products, 1); //100 - 1% = 990

        assertEquals(990, totalInvoice);
    }

    @Test
    void testTotalComDescontoComQuantidadeNegativa(){
        List<Product> products = List.of(
            new Product("Product 1", (float) 50.0, 6),
            new Product("Product 2", (float) 100.0, -3),
            new Product("Product 3", (float) 20.0, 1)
        );

        assertThrows(InvalidProductException.class, () -> invoices.calculateInvoice(products, 1)); // quantidade negativa = InvalidProductException
    }

    @Test 
    void testTotalComDescontoComPrecoNegativo(){
        List<Product> products = List.of(
            new Product("Product 1", (float) -50.0, 2),
            new Product("Product 2", (float) 5.0, 50)
        );

        assertThrows(InvalidProductException.class, () -> invoices.calculateInvoice(products, 8)); // preco negativo = InvalidProductException
    }

    @Test
    void testTotalComDescontoComQuantidadeEPrecoNegativo(){
        List<Product> products = List.of(
            new Product("Product 1", (float) -72.0, -10),
            new Product("Product 2", (float) 83.3, 10)
        );

        assertThrows(InvalidProductException.class, () -> invoices.calculateInvoice(products,  8)); // quantidade e preco negativos = InvalidProductException
    }

    @Test 
    void testTotalComDescontoSemProdutos(){
        List<Product> products = List.of();

        float totalInvoice = invoices.calculateInvoice(products, 1); //0.0 - 1% = 0

        assertEquals(0.0, totalInvoice);
    }

}
