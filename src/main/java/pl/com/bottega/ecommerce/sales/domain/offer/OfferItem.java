/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class OfferItem {

    private ProductData productData;
    private Discount discount;
    private int quantity;
    private Money totalCost;

    public OfferItem(ProductData productData, int quantity) {
        this.productData = productData;
        this.quantity = quantity;
    }

    public OfferItem(ProductData productData, Discount discount, int quantity) {
        this.productData = productData;
        this.discount = discount;
        this.quantity = quantity;
        this.totalCost.setValue(productData.getPrice().getValue().multiply(new BigDecimal(quantity))
                .subtract(discount.getDiscount().getValue()));
    }

    /**
     * @param other
     * @param delta acceptable percentage difference
     * @return
     */
    public boolean sameAs(OfferItem other, double delta) {
        if (productData.getPrice().getValue() == null) {
            if (other.productData.getPrice().getValue() != null) {
                return false;
            }
        } else if (!productData.getPrice().getValue().equals(other.productData.getPrice().getValue())) {
            return false;
        }
        if (productData.getName() == null) {
            if (other.productData.getName() != null) {
                return false;
            }
        } else if (!productData.getName().equals(other.productData.getName())) {
            return false;
        }

        if (productData.getId() == null) {
            if (other.productData.getId() != null) {
                return false;
            }
        } else if (!productData.getId().equals(other.productData.getId())) {
            return false;
        }
        if (!productData.getType().equals(other.productData.getType())) {
            return false;
        }

        if (quantity != other.quantity) {
            return false;
        }

        BigDecimal max;
        BigDecimal min;
        if (totalCost.getValue().compareTo(other.totalCost.getValue()) > 0) {
            max = totalCost.getValue();
            min = other.totalCost.getValue();
        } else {
            max = other.totalCost.getValue();
            min = totalCost.getValue();
        }

        BigDecimal difference = max.subtract(min);
        BigDecimal acceptableDelta = max.multiply(BigDecimal.valueOf(delta / 100));

        return acceptableDelta.compareTo(difference) > 0;
    }

    public ProductData getProductData() {
        return productData;
    }

}
