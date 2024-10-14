package com.bar.domain.bar;

public interface IBarRepository {
    public void Add(Bar bar);
    public void Update(Bar bar);
    public Bar Find(BarId id);
}
