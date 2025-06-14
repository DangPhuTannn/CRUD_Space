// package com.example.CRUDSpace.SeedData;

// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.transaction.annotation.Transactional;

// import com.example.CRUDSpace.Model.Entity.Space;
// import com.example.CRUDSpace.Model.Entity.Type;
// import com.example.CRUDSpace.Model.Entity.Equipment;
// import com.example.CRUDSpace.Repository.SpaceRepository;
// import com.example.CRUDSpace.Repository.TypeRepository;
// import com.example.CRUDSpace.Repository.EquipmentRepository;

// import java.util.*;

// /**
// * Seed mặc định:
// * 1. Type : Block – Floor – Room – Inventory
// * 2. Space : Block/Floor/Room theo mẫu bạn cung cấp
// * 3. Space : Inventory (một Inventory dưới mỗi Block)
// * 4. Equip. : “Máy lạnh”, “Máy chiếu”, “Bàn ghế” cho mọi Room & Inventory
// */
// @Configuration
// @RequiredArgsConstructor
// @Slf4j
// public class InitialData {

// private final TypeRepository typeRepo;
// private final SpaceRepository spaceRepo;
// private final EquipmentRepository equipRepo;

// /** Struct tạm để giữ dữ liệu mẫu */
// private record Sample(int id, int typeLevel, int parentId, String name) {
// }

// @Bean
// CommandLineRunner seedData() {
// return args -> seedAll();
// }

// /* ========================================================= */

// @Transactional
// void seedAll() {

// /* ===== 1. Seed Type (nếu chưa có) ===== */
// Map<Integer, Type> levelMap = new HashMap<>();
// levelMap.put(1, saveTypeIfAbsent(1, "Block"));
// levelMap.put(2, saveTypeIfAbsent(2, "Floor"));
// levelMap.put(3, saveTypeIfAbsent(3, "Room"));
// levelMap.put(4, saveTypeIfAbsent(4, "Inventory"));

// /* ===== 2. Seed Space mẫu Block/Floor/Room (duy nhất) ===== */
// if (spaceRepo.count() == 0) {
// List<Sample> samples = List.of(
// new Sample(1, 1, 0, "Block 1"),
// new Sample(2, 2, 1, "Floor 1"),
// new Sample(3, 2, 1, "Floor 2"),
// new Sample(4, 2, 1, "Floor 3"),
// new Sample(5, 3, 2, "Room 101"),
// new Sample(6, 3, 2, "Room 102"),
// new Sample(7, 3, 3, "Room 201"),
// new Sample(8, 3, 3, "Room 202"),
// new Sample(9, 3, 4, "Room 301"),
// new Sample(10, 3, 4, "Room 302"),
// new Sample(11, 3, 1, "Room 402"));

// Map<Integer, UUID> idMap = new HashMap<>();
// for (Sample s : samples) {
// UUID parentUuid = (s.parentId() == 0) ? null : idMap.get(s.parentId());
// Space saved = spaceRepo.save(
// Space.builder()
// .spaceName(s.name())
// .parentId(parentUuid)
// .type(levelMap.get(s.typeLevel()))
// .build());
// idMap.put(s.id(), saved.getSpaceId());
// }
// log.info("Seeded Block/Floor/Room spaces");
// }

// /* ===== 3. Seed Inventory Space dưới mỗi Block ===== */
// List<Space> blocks = spaceRepo.findAllByType_TypeName("Block");
// for (Space block : blocks) {
// String invName = "Inventory – " + block.getSpaceName();
// if (spaceRepo.existsBySpaceName(invName))
// continue; // đã có

// Space inventory = Space.builder()
// .spaceName(invName)
// .parentId(block.getSpaceId())
// .type(levelMap.get(4)) // Inventory
// .build();
// spaceRepo.save(inventory);
// log.info("Created Inventory space: {}", invName);
// }

// /* ===== 4. Seed Equipment cho mọi Room & Inventory ===== */
// List<Space> targetSpaces = spaceRepo
// .findAllByType_TypeNameIn(List.of("Room", "Inventory"));

// for (Space space : targetSpaces) {
// if (equipRepo.existsBySpace(space))
// continue; // tránh double‑seed
// seedEquipments(space);
// }
// }

// /* ---------- Helpers ---------- */

// private Type saveTypeIfAbsent(int level, String name) {
// return typeRepo.findByTypeName(name)
// .orElseGet(() -> typeRepo.save(Type.builder()
// .level(level)
// .typeName(name)
// .build()));
// }

// private void seedEquipments(Space space) {
// List<String> baseNames = List.of("Máy lạnh", "Máy chiếu", "Bàn ghế");
// baseNames.forEach(base -> {
// equipRepo.save(Equipment.builder()
// .equipmentName(base + " - " + space.getSpaceName()) // duy nhất
// .space(space)
// .build());
// });
// log.info("Seeded equipments for {}", space.getSpaceName());
// }
// }
