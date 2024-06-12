//package com.example.ecommerce.controller.web;
//
//
//import com.example.ecommerce.entity.CartItem;
//import com.example.ecommerce.entity.OrderItem;
//import com.example.ecommerce.service.VNPAYService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import com.example.ecommerce.entity.Order;
//import com.example.ecommerce.service.OrderService;
//import com.example.ecommerce.service.ProductService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.io.UnsupportedEncodingException;
//import java.math.BigDecimal;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.text.SimpleDateFormat;
//import java.util.*;
//@Controller
//@Slf4j
//public class VNPAYController {
//    @Autowired
//    private  ProductService productService;
//
//    @Autowired
//    private  OrderService orderService;
//
//
//
//    @RequestMapping(value = {"/api/payment/create_payment"}, method = RequestMethod.POST)
//    public ResponseEntity<Map<String, String>> createPayment(HttpServletRequest req, Model model,
//                                                             @RequestBody Map<String, String> payload) throws UnsupportedEncodingException {
//        Map<String, Object> jsonResult = new HashMap<>();
//
//        String customerFullName = payload.get("customer_name");
//        String customerAddress = payload.get("customer_address");
//        String customerEmail = payload.get("customer_email");
//        String customerPhone = payload.get("customer_phone");
//        log.info("--------customer_name : " + customerFullName);
//
//        Order order = new Order();
//
//
//        order.setReceiverName(customerFullName);
//        order.setReceiverPhone(customerPhone);
//        order.setReceiverAddress(customerAddress);
//        order.setPaymentType(2); // assuming payment type is VNPAY
//        order.setStatus(1); // assuming initial status of order
//
//        List<OrderItem> orderItems = new ArrayList<>();
//        BigDecimal totalPrices = BigDecimal.ZERO;
//
//        for (CartItem cartItem : cart.getCartItems()) {
//            OrderItem orderItem = new OrderItem();
//            Product product = productService.getById(cartItem.getProductId());
//
//            orderItem.setProduct(product);
//            orderItem.setQuantity(cartItem.getQuanlity());
//            orderItem.setPriceUnit(product.getPrice()); // assuming price per unit
//
//            totalPrices = totalPrices.add(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuanlity())));
//            orderItems.add(orderItem);
//        }
//
//        order.setOrderItems(orderItems);
//        order.setTotalAmount(totalPrices);
//        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//        order.setModifiedAt(new Timestamp(System.currentTimeMillis()));
//        order.setBuyer(user); // assuming user is the currently logged-in buyer
//
//// Giảm số lượng sản phẩm trong kho và cập nhật cơ sở dữ liệu
//        for (OrderItem orderItem : orderItems) {
//            Product product = orderItem.getProduct();
//            int remainingQuantity = product.getQuantity() - orderItem.getQuantity();
//            if (remainingQuantity < 0) {
//                // Handle insufficient quantity error
//                jsonResult.put("error", "Xin lỗi, sản phẩm " + product.getTitle() + " đã hết");
//                return; // Stop processing further
//            } else {
//                product.setQuantity(remainingQuantity);
//                productService.saveOrUpdate(product);
//            }
//        }
//
//// Lưu đơn hàng và thông tin sản phẩm đặt hàng vào cơ sở dữ liệu
//        order.setCreatedAt(new Timestamp(System.currentTimeMillis())); // assuming current time as creation time
//        order.setModifiedAt(new Timestamp(System.currentTimeMillis())); // assuming current time as modification time
//        orderService.saveOrUpdate(order);
//        String orderType = "other";
//        long amount = totalPrice * 100L;
//        String vnp_TxnRef = VnpayConfig.getRandomNumber(8);
//        String vnp_IpAddr = VnpayConfig.getIpAddress(req);
//        String vnp_TmnCode = VnpayConfig.vnp_TmnCode;
//
//        Map<String, String> vnp_Params = new HashMap<>();
//        vnp_Params.put("vnp_Version", VnpayConfig.vnp_Version);
//        vnp_Params.put("vnp_Command", VnpayConfig.vnp_Command);
//        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
//        vnp_Params.put("vnp_Amount", String.valueOf(amount));
//        vnp_Params.put("vnp_CurrCode", "VND");
//        vnp_Params.put("vnp_BankCode", "NCB");
//        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
//        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
//        vnp_Params.put("vnp_Locale", "vn");
//        vnp_Params.put("vnp_OrderType", orderType);
//        vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_ReturnUrl);
//        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
//
//        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        String vnp_CreateDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//
//        cld.add(Calendar.MINUTE, 10);
//        String vnp_ExpireDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
//
//        List fieldNames = new ArrayList(vnp_Params.keySet());
//        Collections.sort(fieldNames);
//        StringBuilder hashData = new StringBuilder();
//        StringBuilder query = new StringBuilder();
//        Iterator itr = fieldNames.iterator();
//        while (itr.hasNext()) {
//            String fieldName = (String) itr.next();
//            String fieldValue = (String) vnp_Params.get(fieldName);
//            if ((fieldValue != null) && (fieldValue.length() > 0)) {
//                //Build hash data
//                hashData.append(fieldName);
//                hashData.append('=');
//                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                //Build query
//                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
//                query.append('=');
//                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                if (itr.hasNext()) {
//                    query.append('&');
//                    hashData.append('&');
//                }
//            }
//        }
//        String queryUrl = query.toString();
//        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData.toString());
//        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
//        String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
//
//        PaymentResDto paymentResDto = new PaymentResDto();
//        paymentResDto.setStatus("OK");
//        paymentResDto.setMessage("Successfully");
//        paymentResDto.setUrl(paymentUrl);
//
//        model.addAttribute("paymentUrl", paymentUrl);
//        Map<String, String> responseData = new HashMap<>();
//        responseData.put("paymentUrl", paymentUrl);
//
//        // Trả về đối tượng ResponseEntity chứa thông tin phản hồi
//        return ResponseEntity.ok(responseData);
//    }
//
////@Controller
////public class VNPAYController {
////    @Autowired
////    private VNPAYService vnPayService;
////
////
////    @PostMapping("/submitOrder")
////    public String submidOrder(@RequestParam("amount") int orderTotal,
////                              @RequestParam("orderInfo") String orderInfo,
////                              HttpServletRequest request){
////        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
////        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
////        return "redirect:" + vnpayUrl;
////    }
////
////    @GetMapping("/vnpay-payment")
////    public String GetMapping(HttpServletRequest request, Model model){
////        int paymentStatus =vnPayService.orderReturn(request);
////
////        String orderInfo = request.getParameter("vnp_OrderInfo");
////        String paymentTime = request.getParameter("vnp_PayDate");
////        String transactionId = request.getParameter("vnp_TransactionNo");
////        String totalPrice = request.getParameter("vnp_Amount");
////
////        model.addAttribute("orderId", orderInfo);
////        model.addAttribute("totalPrice", totalPrice);
////        model.addAttribute("paymentTime", paymentTime);
////        model.addAttribute("transactionId", transactionId);
////
////        return paymentStatus == 1 ? "payment/ordersuccess" : "payment/orderfail";
////    }
////}
