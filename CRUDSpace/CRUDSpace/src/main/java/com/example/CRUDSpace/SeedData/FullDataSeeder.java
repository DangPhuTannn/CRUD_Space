// package com.example.CRUDSpace.SeedData;

// import com.example.CRUDSpace.Model.Entity.*;
// import com.example.CRUDSpace.Repository.*;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.transaction.annotation.Transactional;

// import java.time.LocalDateTime;
// import java.util.*;

// /**
//  * Chạy một lần sau khi bạn vừa drop & tạo lại toàn bộ bảng.
//  * 1. Seed Type (Block / Floor / Room / Inventory)
//  * 2. Seed Space (Block1 > Floor{1,2,3} > Rooms … & Inventory dưới Block)
//  * 3. Seed Provider, Value
//  * 4. Seed Equipment (Máy lạnh + Máy chiếu cho mọi Room & Inventory)
//  * 5. Seed EquipmentState (3 giá trị demo / mỗi thiết bị)
//  */
// @Configuration
// @RequiredArgsConstructor
// @Slf4j
// public class FullDataSeeder {

//     private final ValueRepository valueRepo;
//     private final EquipmentRepository equipRepo;
//     private final EquipmentStateRepository stateRepo;

//     @Bean
//     CommandLineRunner seedAll() {
//         return args -> doSeed();
//     }

//     /* =========================================================== */
//     @Transactional
//     void doSeed() {

//         List<Value> values = valueRepo.findAll();

//         /* 5️⃣ EQUIPMENT_STATE (mỗi equip × mọi value) ------------ */
//         List<Equipment> equips = equipRepo.findAll();
//         equips.forEach(eq -> values.forEach(vl -> stateRepo.save(EquipmentState.builder()
//                 .equipment(eq)
//                 .value(vl)
//                 .timeStamp(LocalDateTime.now().toString())
//                 .valueResponse(sampleValue(eq.getEquipmentName(), vl.getValueName()))
//                 .build())));

//         log.info("=== SEED DONE ===");
//     }

//     /* ------------ demo valueResponse --------------------------- */
//     private String sampleValue(String eq, String val) {
//         return switch (eq + "|" + val) {
//             case "Máy l?nh|Nhi?t d? cài d?t" -> "24°C";
//             case "Máy l?nh|Tr?ng thái ngu?n" -> "ON";
//             case "Máy chi?u|Tr?ng thái ngu?n" -> "OFF";
//             case "Máy chi?u|Đ? sáng" -> "1500 lm";
//             default -> "N/A";
//         };
//     }
// }
