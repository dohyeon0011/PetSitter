package com.PetSitter.dto.Reservation.CustomerReservation.response;

import com.PetSitter.domain.CareLog.CareLog;
import com.PetSitter.domain.Member.Member;
import com.PetSitter.domain.Pet.PetReservation;
import com.PetSitter.domain.Reservation.CustomerReservation.CustomerReservation;
import com.PetSitter.domain.Reservation.CustomerReservation.ReservationStatus;
import com.PetSitter.domain.Review.Review;
import com.PetSitter.dto.CareLog.response.CareLogResponse;
import com.PetSitter.dto.Pet.response.PetReservationResponse;
import com.PetSitter.dto.Review.response.ReviewResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public class CustomerReservationResponse { // 고객 시점 예약 조회

    @NoArgsConstructor
    @Getter
    public static class GetList {
        private long id;
        private String sitterName;
        private LocalDate reservationAt;
        private LocalDateTime createdAt;
        private ReservationStatus status;

        public GetList(long id, String sitterName, LocalDate reservationAt, LocalDateTime createdAt, ReservationStatus status) {
            this.id = id;
            this.sitterName = sitterName;
            this.reservationAt = reservationAt;
            this.createdAt = createdAt;
            this.status = status;
        }
    }

    @NoArgsConstructor
    @Getter
    public static class GetDetail {
        private long id;
        private long customerId;
        private String customerNickName;
        private String sitterName;
        private int price;
        private LocalDate reservationAt;
        private String zipcode;
        private String address;
        private String requests;
        private LocalDateTime createdAt;
        private ReservationStatus status;
        private List<PetReservationResponse> pets;
        private List<CareLogResponse.GetDetail> careLogList;
        private ReviewResponse.GetDetail review;

        public GetDetail(Member customer, Member sitter, CustomerReservation customerReservation, List<PetReservation> pets, List<CareLog> careLogList, Review review) {
            this.id = customerReservation.getId();
            this.customerId = customer.getId();
            this.customerNickName = customer.getNickName();
            this.sitterName = sitter.getName();
            this.price = customerReservation.getPrice();
            this.reservationAt = customerReservation.getReservationAt();
            this.zipcode = sitter.getZipcode();
            this.address = sitter.getAddress();
            this.requests = customerReservation.getRequests();
            this.createdAt = customerReservation.getCreatedAt();
            this.status = customerReservation.getStatus();
            this.pets = pets
                    .stream()
                    .map(PetReservationResponse::new)
                    .toList();
            /*this.careLogList = careLogList
                    .stream()
                    .map(CareLogResponse.GetDetail::new)
                    .toList();*/
            this.careLogList = careLogList
                    .stream()
                    .map(careLog -> new CareLogResponse.GetDetail(
                            careLog.getId(),
                            careLog.getSitterSchedule().getSitter().getName(),
                            careLog.getCareType(),
                            careLog.getDescription(),
                            careLog.getImgPath(),
                            careLog.getCreatedAt()
                    ))
                    .toList();
//            this.review = Optional.of(new ReviewResponse.GetDetail(review.getId(), review.getCustomerReservation().getId(), review.getCustomerReservation().getCustomer().getNickName(),
//                    review.getCustomerReservation().getSitter().getName(), review.getRating(), review.getComment()))
//                    .orElse(new ReviewResponse.GetDetail());
        }
    }

}
