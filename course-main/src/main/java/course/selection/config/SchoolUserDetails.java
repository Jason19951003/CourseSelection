package course.selection.config;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import course.selection.model.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolUserDetails implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public enum Permission {
	    ADMIN, TEACHER, STUDENT;
	}

	public Permission getPermission() {
		if (user.getPermissionId() == 1) {
            return Permission.ADMIN;
        } else if (user.getPermissionId() == 2) {
            return Permission.TEACHER;
        } else if (user.getPermissionId() == 3) {
            return Permission.STUDENT;
        }
		return null; 
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        // 根據 permissionId 設定對應的權限
        if (user.getPermissionId() == 1) {
            return Stream.of(new SimpleGrantedAuthority("ROLE_ADMIN")).collect(Collectors.toList());
        } else if (user.getPermissionId() == 2) {
            return Stream.of(new SimpleGrantedAuthority("ROLE_TEACHER")).collect(Collectors.toList());
        } else if (user.getPermissionId() == 3) {
            return Stream.of(new SimpleGrantedAuthority("ROLE_STUDENT")).collect(Collectors.toList());
        }
        return Collections.emptyList();
	}

	@Override
	public String getPassword() {
		return user.getUserPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
