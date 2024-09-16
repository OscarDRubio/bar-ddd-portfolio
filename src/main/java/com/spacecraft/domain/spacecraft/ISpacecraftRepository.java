package com.spacecraft.domain.spacecraft;

public interface ISpacecraftRepository {
    public void Add(Spacecraft spacecraft);
    public void Update(Spacecraft spacecraft);
    public Spacecraft Find(SpacecraftId id);
}
