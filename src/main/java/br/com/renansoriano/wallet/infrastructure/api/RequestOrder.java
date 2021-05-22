package br.com.renansoriano.wallet.infrastructure.api;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "user_id",
    "buy_date",
    "type",
    "financial_institute",
    "stock",
    "quantity",
    "price",
    "brokerage_price"
})
public class RequestOrder {
	
	@JsonProperty("id")
	private UUID id;
	
	@JsonProperty("user_id")
	private UUID userId;
	
	@JsonProperty("buy_date")
	private ZonedDateTime buyDate;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("financial_institute")
	private String financialInstitute;
	
	@JsonProperty("stock")
	private String stock;
	
	@JsonProperty("quantity")
	private Integer quantity;
	
	@JsonProperty("price")
	private BigDecimal price;
	
	@JsonProperty("brokerage_price")
	private BigDecimal brokeragePrice;
	
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(UUID id) {
        this.id = id;
    }

    @JsonProperty("user_id")
    public UUID getUserId() {
        return userId;
    }

    @JsonProperty("user_id")
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @JsonProperty("buy_date")
    public ZonedDateTime getBuyDate() {
        return buyDate;
    }

    @JsonProperty("buy_date")
    public void setBuyDate(ZonedDateTime buyDate) {
        this.buyDate = buyDate;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("financial_institute")
    public String getFinancialInstitute() {
        return financialInstitute;
    }

    @JsonProperty("financial_institute")
    public void setFinancialInstitute(String financialInstitute) {
        this.financialInstitute = financialInstitute;
    }

    @JsonProperty("stock")
    public String getStock() {
        return stock;
    }

    @JsonProperty("stock")
    public void setStock(String stock) {
        this.stock = stock;
    }

    @JsonProperty("quantity")
    public Integer getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("price")
    public BigDecimal getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @JsonProperty("brokerage_price")
    public BigDecimal getBrokeragePrice() {
        return brokeragePrice;
    }

    @JsonProperty("brokerage_price")
    public void setBrokeragePrice(BigDecimal brokeragePrice) {
        this.brokeragePrice = brokeragePrice;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
