//package org.uengine.monetization;
//
//import org.metaworks.ServiceMethodContext;
//import org.metaworks.annotation.AutowiredFromClient;
//import org.metaworks.annotation.Face;
//import org.metaworks.annotation.ServiceMethod;
//import org.metaworks.widget.ModalWindow;
//import org.uengine.application.App;
//import org.uengine.monetization.face.UsageListFace;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.metaworks.dwr.MetaworksRemoteService.wrapReturn;
//
//public class Invoice {
//
//    String id;
//        public String getId() {
//            return id;
//        }
//        public void setId(String id) {
//            this.id = id;
//        }
//
//    String invoiceNumber;
//        public String getInvoiceNumber() {
//            return invoiceNumber;
//        }
//        public void setInvoiceNumber(String invoiceNumber) {
//            this.invoiceNumber = invoiceNumber;
//        }
//
//
//    Date invoiceDate;
//        public Date getInvoiceDate() {
//            return invoiceDate;
//        }
//        public void setInvoiceDate(Date invoiceDate) {
//            this.invoiceDate = invoiceDate;
//        }
//
//
//    Date dueDate;
//        public Date getDueDate() {
//            return dueDate;
//        }
//        public void setDueDate(Date dueDate) {
//            this.dueDate = dueDate;
//        }
//
//
//    double amount;
//        public double getAmount() {
//            return amount;
//        }
//        public void setAmount(double amount) {
//            this.amount = amount;
//        }
//
//
//    double balance;
//        public double getBalance() {
//            return balance;
//        }
//        public void setBalance(double balance) {
//            this.balance = balance;
//        }
//
//
//    String status;
//        public String getStatus() {
//            return status;
//        }
//        public void setStatus(String status) {
//            this.status = status;
//        }
//
//    int recurringTime;
//        public int getRecurringTime() {
//            return recurringTime;
//        }
//        public void setRecurringTime(int recurringTime) {
//            this.recurringTime = recurringTime;
//        }
//
//    List<Usage> usageList = new ArrayList<Usage>();
//    @Face(faceClass=UsageListFace.class)
//        public List<Usage> getUsageList() {
//            return usageList;
//        }
//
//        public void setUsageList(List<Usage> usageList) {
//            this.usageList = usageList;
//        }
//
//    Subscription subscription = new Subscription();
//        public Subscription getSubscription() {
//            return subscription;
//        }
//
//        public void setSubscription(Subscription subscription) {
//            this.subscription = subscription;
//        }
//
//
//    @AutowiredFromClient
//    public App app;
//
//    @ServiceMethod(callByContent = true, target= ServiceMethodContext.TARGET_POPUP)
//    public void generatePDF(){
//
//        //get real values
////        for(Plan plan : app.getPlanList()){
////            for(ServiceAndRate serviceAndRate : plan.getOneTimeServiceAndRateList()){
////                serviceAndRate.setService(app.getService(serviceAndRate.getService().getId()));
////            }
////            for(ServiceAndRate serviceAndRate : plan.getRecurringServiceAndRateList()){
////                serviceAndRate.setService(app.getService(serviceAndRate.getService().getId()));
////            }
////            for(ServiceAndRate serviceAndRate : plan.getUsageServiceAndRateList()){
////                serviceAndRate.setService(app.getService(serviceAndRate.getService().getId()));
////            }
////        }
//
//        getSubscription().setPlan(app.getPlan(getSubscription().getPlan().getId()));
//        //end get real values
//
//        //.... calculate first
//        setAmount(
//                getSubscription().bill(getUsageList(), getRecurringTime())
//        );
//
//        //...generates PDF anyway...
//
//        wrapReturn(new ModalWindow(this));
//    }
//}
