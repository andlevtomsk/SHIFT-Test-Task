package service;

import java.math.BigDecimal;

public record Statistics(int countLong, int countDouble, int countString, long sumLong, long maxLong, long minLong, double maxDouble,
                         double minDouble, double sumDouble, int maxLength, int minLength, BigDecimal averageLongValue,
                         BigDecimal averageDoubleValue) {

}
