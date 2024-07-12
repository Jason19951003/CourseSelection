package course.selection.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import course.selection.dao.UserMapper;
import course.selection.util.CamelCaseUtil;
import course.selection.util.FileUtil;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<Map<String, Object>> findUsers(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        return findUsers(map);
    }

    public List<Map<String, Object>> findUsers(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(userMapper.findUsers(param));
    }

    public List<Map<String, Object>> findClassInfo(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(userMapper.findClassInfo(param));
    }

    public Integer insertUser(Map<String, Object> param, MultipartFile avatar) {
        String userId = String.valueOf(param.get("userId"));
        param.put("password", new BCryptPasswordEncoder().encode(userId));
        if (!"".equals(avatar.getOriginalFilename().trim())) {
            param.put("avatar", FileUtil.getAvatarName(avatar, userId));
            FileUtil.uploadFile(avatar, userId);
        }
        
    return userMapper.insertUser(param);
    }

    public Boolean checkUserFile(String year, MultipartFile file) throws IOException {
    	if (!userMapper.checkGrade().isEmpty()) {
    		throw new RuntimeException("請先更新年級，再匯入資料!");
    	}
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                String userId = getCellValue(row.getCell(0))+"";
                String depId = getCellValue(row.getCell(6))+"";
                if (userId.startsWith(depId+year)) {
                } else {
                    throw new RuntimeException("資料錯誤!");
                }
            }
        }
        return true;
    }
    
    @Transactional
    public Integer insertUserFromExcel(String year, MultipartFile file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Map<String, Object> map = new HashMap<>();
                Row row = sheet.getRow(rowNum);
                
                Map<String, Object> classMap = new HashMap<>();
                classMap.put("depId", getCellValue(row.getCell(6)));
                classMap.put("classGrade", 1);
                classMap.put("className", getCellValue(row.getCell(7)));

                map.put("userId", getCellValue(row.getCell(0)));
                map.put("password", encoder.encode(getCellValue(row.getCell(0)) + ""));
                map.put("permissionId", 3);
                map.put("userName", getCellValue(row.getCell(1)));
                map.put("sex", getCellValue(row.getCell(2)));
                map.put("birthDate", getCellValue(row.getCell(3)));
                map.put("email", getCellValue(row.getCell(4)));
                map.put("phone", getCellValue(row.getCell(5)));
                map.put("depId", getCellValue(row.getCell(6)));
                map.put("admissionDate", getCellValue(row.getCell(8)));
                map.put("classId", findClassInfo(classMap).get(0).get("classId"));

                int rowCount = userMapper.insertUser(map);
                if (!(rowCount > 0)) {
                    throw new RuntimeException("新增學生發生錯誤!");
                }                
            }
        }
        return 1;
    }

    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return (int)cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    public Integer updateUser(Map<String, Object> param, MultipartFile avatar) {
        String userId = String.valueOf(param.get("userId"));
        if (!"".equals(avatar.getOriginalFilename().trim())) {
            param.put("avatar", FileUtil.getAvatarName(avatar, userId));
            FileUtil.uploadFile(avatar, userId);
        }
        
        return userMapper.updateUser(param);
    }

    public Integer deleteUser(String userId) {
        return userMapper.deleteUser(userId);
    }

    public Map<String, Object> findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }

    public Integer updatePassword(Map<String, Object> param) {
        // 將密碼加密存進資料庫
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        param.put("password", encoder.encode(param.get("password")+""));
        return userMapper.updatePassword(param);
    }

    // 更新年級
    public Integer updateCurrentStudent() {
        return userMapper.updateCurrentStudent();
    }
}
