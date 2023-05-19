// Generated by Dagger (https://dagger.dev).
package com.purple.hello.domain.setting.room;

import com.purple.data.rooms.repository.RoomRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class UpdateUserNameUseCase_Factory implements Factory<UpdateUserNameUseCase> {
  private final Provider<RoomRepository> roomRepositoryProvider;

  public UpdateUserNameUseCase_Factory(Provider<RoomRepository> roomRepositoryProvider) {
    this.roomRepositoryProvider = roomRepositoryProvider;
  }

  @Override
  public UpdateUserNameUseCase get() {
    return newInstance(roomRepositoryProvider.get());
  }

  public static UpdateUserNameUseCase_Factory create(
      Provider<RoomRepository> roomRepositoryProvider) {
    return new UpdateUserNameUseCase_Factory(roomRepositoryProvider);
  }

  public static UpdateUserNameUseCase newInstance(RoomRepository roomRepository) {
    return new UpdateUserNameUseCase(roomRepository);
  }
}
