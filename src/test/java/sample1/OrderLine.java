package sample1;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderLine {
  private int lineNo;
  private String productId;
  private int qty;
}
